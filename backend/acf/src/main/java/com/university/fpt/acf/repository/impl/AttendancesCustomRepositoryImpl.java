package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ExportExcelForm;
import com.university.fpt.acf.repository.AttendancesCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AttendancesCustomRepositoryImpl extends CommonRepository implements AttendancesCustomRepository {
    @Override
    public List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.AttendanceVO(t.id,t.date,e.id,a.username,t.type,t.note) from Account a inner join a.employee e inner join e.timeKeeps t where 1=1");
        if (attendanceFrom.getName() != null && !attendanceFrom.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(a.username) like :name ");
            paramsAcc.put("name", "%" + attendanceFrom.getName().toLowerCase() + "%");
        }
        if (attendanceFrom.getDate() != null && !attendanceFrom.getDate().isEmpty()) {
            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd ");
            paramsAcc.put("dateStart", attendanceFrom.getDate().get(0));
            paramsAcc.put("dateEnd", attendanceFrom.getDate().get(1));
        }
        if(attendanceFrom.getType() != null && !attendanceFrom.getType().isEmpty()){
            sqlAcc.append(" and t.type = :type ");
            paramsAcc.put("type",attendanceFrom.getType());
        }
        if(attendanceFrom.getNote() != null && !attendanceFrom.getNote().isEmpty()){
            sqlAcc.append(" and LOWER(t.note) like :note ");
            paramsAcc.put("note","%" +attendanceFrom.getNote()+"%");
        }
        sqlAcc.append(" ORDER by t.date desc ");
        TypedQuery<AttendanceVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, AttendanceVO.class);
        queryAcc.setFirstResult((attendanceFrom.getPageIndex() - 1)*attendanceFrom.getPageSize());
        queryAcc.setMaxResults(attendanceFrom.getPageSize());
        List<AttendanceVO> resultList = queryAcc.getResultList();
        return resultList;
    }

    @Override
    public List<AttendanceVO> priviewDetailExcel(ExportExcelForm exportExcelForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.AttendanceVO(t.id,t.date,e.id,e.fullName,t.type,t.note) from Account a inner  join a.employee e inner  join e.timeKeeps t where 1=1");
        if (exportExcelForm.getDataSearch().getName() != null && !exportExcelForm.getDataSearch().getName().isEmpty()) {
            sqlAcc.append(" and LOWER(a.username) like :name ");
            paramsAcc.put("name", "%" + exportExcelForm.getDataSearch().getName().toLowerCase() + "%");
        }
        if (exportExcelForm.getDataSearch().getDate() != null && !exportExcelForm.getDataSearch().getDate().isEmpty()) {
            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd ");
            paramsAcc.put("dateStart", exportExcelForm.getDataSearch().getDate().get(0));
            paramsAcc.put("dateEnd", exportExcelForm.getDataSearch().getDate().get(1));
        }
        if(exportExcelForm.getDataSearch().getType() != null && !exportExcelForm.getDataSearch().getType().isEmpty()){
            sqlAcc.append(" and t.type = :type ");
            paramsAcc.put("type",exportExcelForm.getDataSearch().getType());
        }
        if(exportExcelForm.getDataSearch().getNote() != null && !exportExcelForm.getDataSearch().getNote().isEmpty()){
            sqlAcc.append(" and LOWER(t.note) like :note ");
            paramsAcc.put("note","%" +exportExcelForm.getDataSearch().getNote()+"%");
        }
        sqlAcc.append(" ORDER by t.date ASC ");
        TypedQuery<AttendanceVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, AttendanceVO.class);
        List<AttendanceVO> resultList = queryAcc.getResultList();
        return resultList;
    }

    @Override
    public int getTotalAttendances(AttendanceFrom attendanceFrom) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select COUNT(*) from Account a inner join a.employee e inner join e.timeKeeps t where t.deleted = false ");
        if (attendanceFrom.getName() != null && !attendanceFrom.getName().isEmpty()) {
            sqlAcc.append(" and LOWER(a.username) like :name ");
            paramsAcc.put("name", "%" + attendanceFrom.getName().toLowerCase() + "%");
        }
        if (attendanceFrom.getDate() != null && !attendanceFrom.getDate().isEmpty()) {
            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd ");
            paramsAcc.put("dateStart", attendanceFrom.getDate().get(0));
            paramsAcc.put("dateEnd", attendanceFrom.getDate().get(1));
        }
        if(attendanceFrom.getType() != null && !attendanceFrom.getType().isEmpty()){
            sqlAcc.append(" and t.type = :type ");
            paramsAcc.put("type",attendanceFrom.getType());
        }
        if(attendanceFrom.getNote() != null && !attendanceFrom.getNote().isEmpty()){
            sqlAcc.append(" and LOWER(t.note) like :note ");
            paramsAcc.put("note","%" +attendanceFrom.getNote()+"%");
        }
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        return queryAcc.getSingleResult().intValue();
    }
}
