package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.EmployeeNotAttendanceForm;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.GetAllAccountVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmployeeCustomRepositoryImpl extends CommonRepository implements EmployeeCustomRepository {
    @Override
    public List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select t.employee.id from TimeKeep t where t.deleted = false ");
        if (employeeNotAttendanceForm.getDate() != null) {
            sqlAcc.append(" and t.date = :date  ");
            paramsAcc.put("date", employeeNotAttendanceForm.getDate());
        }
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        List<Long> listID = queryAcc.getResultList();

        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new  com.university.fpt.acf.vo.GetAllEmployeeVO(e.id,e.fullName) " +
                " from Employee e where 1=1 ");

        if(listID != null && listID.size()!=0){
            sql.append(" and e.id NOT IN :listId ");
            params.put("listId",listID);

        }
        TypedQuery<GetAllEmployeeVO> query = super.createQuery(sql.toString(),params, GetAllEmployeeVO.class);
        query.setFirstResult(employeeNotAttendanceForm.getPageIndex()-1);
        query.setMaxResults(employeeNotAttendanceForm.getPageSize());
        List<GetAllEmployeeVO> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select t.employee.id from TimeKeep t where t.deleted = false ");
        if (employeeNotAttendanceForm.getDate() != null) {
            sqlAcc.append(" and t.date = :date  ");
            paramsAcc.put("date", employeeNotAttendanceForm.getDate());
        }
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        List<Long> listID = queryAcc.getResultList();

        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select e.id " +
                " from Employee e where e.deleted = false ");

        if(listID != null && listID.size()!=0){
            sql.append(" and e.id NOT IN :listId ");
            params.put("listId",listID);

        }
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        List<Long> resultList = query.getResultList();
        return resultList.size();
    }
}
