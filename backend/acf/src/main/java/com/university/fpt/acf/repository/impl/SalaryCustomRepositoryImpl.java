package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.repository.SalaryCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.SearchSalaryVO;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryCustomRepositoryImpl extends CommonRepository implements SalaryCustomRepository {
    @Override
    public List<SearchSalaryVO> searchSalary(SearchSalaryForm searchSalaryForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.SearchSalaryVO(hs.id,hs.created_date,e.fullName,p.name,hs.countWork,hs.salary" +
                ",hs.bonus,hs.penalty,hs.advanceSalary,hs.totalMoney,hs.status) " +
                " from HistorySalary hs inner join hs.employee e inner join e.position p where 1=1");
//        if (attendanceFrom.getName() != null && !attendanceFrom.getName().isEmpty()) {
//            sqlAcc.append(" and LOWER(e.fullName) like :name ");
//            paramsAcc.put("name", "%" + attendanceFrom.getName().toLowerCase() + "%");
//        }
//        if (attendanceFrom.getDate() != null && !attendanceFrom.getDate().isEmpty()) {
//            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd ");
//            paramsAcc.put("dateStart", attendanceFrom.getDate().get(0));
//            paramsAcc.put("dateEnd", attendanceFrom.getDate().get(1));
//        }
//        if(attendanceFrom.getType() != null && !attendanceFrom.getType().isEmpty()){
//            sqlAcc.append(" and t.type = :type ");
//            paramsAcc.put("type",attendanceFrom.getType());
//        }
//        if(attendanceFrom.getNote() != null && !attendanceFrom.getNote().isEmpty()){
//            sqlAcc.append(" and LOWER(t.note) like :note ");
//            paramsAcc.put("note","%" +attendanceFrom.getNote()+"%");
//        }
        sqlAcc.append(" ORDER by t.date desc ");
        TypedQuery<SearchSalaryVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, SearchSalaryVO.class);
        queryAcc.setFirstResult((searchSalaryForm.getPageIndex() - 1)*searchSalaryForm.getPageSize());
        queryAcc.setMaxResults(searchSalaryForm.getPageSize());
        List<SearchSalaryVO> resultList = queryAcc.getResultList();
        return resultList;
    }

    @Override
    public int getTotalSearchSalary(SearchSalaryForm searchSalaryForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select COUNT(*) from TimeKeep t inner join t.employee e where t.deleted = false ");
//        if (attendanceFrom.getName() != null && !attendanceFrom.getName().isEmpty()) {
//            sqlAcc.append(" and LOWER(e.fullName) like :name ");
//            paramsAcc.put("name", "%" + attendanceFrom.getName().toLowerCase() + "%");
//        }
//        if (attendanceFrom.getDate() != null && !attendanceFrom.getDate().isEmpty()) {
//            sqlAcc.append(" and  t.date BETWEEN :dateStart and :dateEnd ");
//            paramsAcc.put("dateStart", attendanceFrom.getDate().get(0));
//            paramsAcc.put("dateEnd", attendanceFrom.getDate().get(1));
//        }
//        if(attendanceFrom.getType() != null && !attendanceFrom.getType().isEmpty()){
//            sqlAcc.append(" and t.type = :type ");
//            paramsAcc.put("type",attendanceFrom.getType());
//        }
//        if(attendanceFrom.getNote() != null && !attendanceFrom.getNote().isEmpty()){
//            sqlAcc.append(" and LOWER(t.note) like :note ");
//            paramsAcc.put("note","%" +attendanceFrom.getNote()+"%");
//        }
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        return queryAcc.getSingleResult().intValue();
    }
}
