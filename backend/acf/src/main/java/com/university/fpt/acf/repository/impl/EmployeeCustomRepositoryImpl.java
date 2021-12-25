package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.EmployeeNotAttendanceForm;
import com.university.fpt.acf.form.SearchAllEmployeeForm;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.vo.*;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.ArrayList;
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
        sql.append(" select new  com.university.fpt.acf.vo.GetAllEmployeeVO(e.id,a.username) " +
                " from Account a inner  join a.employee e where a.deleted = false and e.deleted = false ");

        if (listID != null && listID.size() != 0) {
            sql.append(" and e.id NOT IN :listId ");
            params.put("listId", listID);

        }
        TypedQuery<GetAllEmployeeVO> query = super.createQuery(sql.toString(), params, GetAllEmployeeVO.class);
        List<GetAllEmployeeVO> resultList = query.getResultList();
        return resultList;
    }

    @Override
    public List<GetAllEmployeeVO> getAllEmployeeNotAttendanceJob() {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select t.employee.id from TimeKeep t where t.deleted = false ");
        sqlAcc.append(" and t.date = :date  ");
        paramsAcc.put("date", LocalDate.now());
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, Long.class);
        List<Long> listID = queryAcc.getResultList();

        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new  com.university.fpt.acf.vo.GetAllEmployeeVO(e.id,e.fullName) " +
                " from Employee e where 1=1 ");
        if (listID != null && listID.size() != 0) {
            sql.append(" and e.id NOT IN :listId ");
            params.put("listId", listID);

        }
        TypedQuery<GetAllEmployeeVO> query = super.createQuery(sql.toString(), params, GetAllEmployeeVO.class);
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
                " from Account a inner  join a.employee e where e.deleted = false ");

        if (listID != null && listID.size() != 0) {
            sql.append(" and e.id NOT IN :listId ");
            params.put("listId", listID);

        }
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        List<Long> resultList = query.getResultList();
        return resultList.size();
    }

    @Override
    public List<SearchEmployeeVO> searchEmployee(SearchAllEmployeeForm searchAllEmployeeForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.SearchEmployeeVO(e.id,img.name,e.fullName,e.gender,e.dob,e.email,p.id,p.name,e.deleted) " +
                "FROM Employee e left join e.image img left join e.position p where  1=1 ");
        if (searchAllEmployeeForm.getName() != null && !searchAllEmployeeForm.getName().isEmpty()) {
            sql.append(" and LOWER(e.fullName) like :name");
            params.put("name", "%" + searchAllEmployeeForm.getName().toLowerCase() + "%");
        }
        if (searchAllEmployeeForm.getIdPosition() != null) {
            sql.append(" and p.id = :id ");
            params.put("id", searchAllEmployeeForm.getIdPosition());
        }
        if (searchAllEmployeeForm.getStatusDelete() != null) {
            sql.append(" and  e.deleted = :delete");
            params.put("delete", searchAllEmployeeForm.getStatusDelete());
        }
        sql.append(" ORDER by e.id desc");
        TypedQuery<SearchEmployeeVO> query = super.createQuery(sql.toString(), params, SearchEmployeeVO.class);
        query.setFirstResult((searchAllEmployeeForm.getPageIndex() - 1) * searchAllEmployeeForm.getPageSize());
        query.setMaxResults(searchAllEmployeeForm.getPageSize());
        List<SearchEmployeeVO> positionList = query.getResultList();
        return positionList;
    }

    @Override
    public int getTotalEmployee(SearchAllEmployeeForm searchAllEmployeeForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) FROM Employee e left join e.position p where 1=1 ");
        if (searchAllEmployeeForm.getName() != null && !searchAllEmployeeForm.getName().isEmpty()) {
            sql.append(" and LOWER(e.fullName) like :name");
            params.put("name", "%" + searchAllEmployeeForm.getName().toLowerCase() + "%");
        }
        if (searchAllEmployeeForm.getIdPosition() != null) {
            sql.append(" and p.id = :id ");
            params.put("id", searchAllEmployeeForm.getIdPosition());
        }
        if (searchAllEmployeeForm.getStatusDelete() != null) {
            sql.append(" and  e.deleted = :delete");
            params.put("delete", searchAllEmployeeForm.getStatusDelete());
        }
        sql.append(" ORDER by e.id desc");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<SearchEmployeeVO> searchEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.SearchEmployeeVO(e.id,img.name,e.fullName,e.gender,e.dob,e.email,p.id,p.name,e.deleted) " +
                "FROM Employee e left join e.image img left join e.position p where  e.deleted = false ");
        if (searchAllEmployeeForm.getName() != null && !searchAllEmployeeForm.getName().isEmpty()) {
            sql.append(" and LOWER(e.fullName) like :name");
            params.put("name", "%" + searchAllEmployeeForm.getName().toLowerCase() + "%");
        }
        if (searchAllEmployeeForm.getIdPosition() != null) {
            sql.append(" and p.id = :id ");
            params.put("id", searchAllEmployeeForm.getIdPosition());
        }
        if (searchAllEmployeeForm.getStatusDelete() != null) {
            sql.append(" and  e.deleted = :delete");
            params.put("delete", searchAllEmployeeForm.getStatusDelete());
        }
        sql.append(" ORDER by e.id desc");
        TypedQuery<SearchEmployeeVO> query = super.createQuery(sql.toString(), params, SearchEmployeeVO.class);
        query.setFirstResult((searchAllEmployeeForm.getPageIndex() - 1) * searchAllEmployeeForm.getPageSize());
        query.setMaxResults(searchAllEmployeeForm.getPageSize());
        List<SearchEmployeeVO> positionList = query.getResultList();
        return positionList;
    }

    @Override
    public int getTotalEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) FROM Employee e left join e.position p where e.deleted = false ");
        if (searchAllEmployeeForm.getName() != null && !searchAllEmployeeForm.getName().isEmpty()) {
            sql.append(" and LOWER(e.fullName) like :name");
            params.put("name", "%" + searchAllEmployeeForm.getName().toLowerCase() + "%");
        }
        if (searchAllEmployeeForm.getIdPosition() != null) {
            sql.append(" and p.id = :id ");
            params.put("id", searchAllEmployeeForm.getIdPosition());
        }
        if (searchAllEmployeeForm.getStatusDelete() != null) {
            sql.append(" and  e.deleted = :delete");
            params.put("delete", searchAllEmployeeForm.getStatusDelete());
        }
        sql.append(" ORDER by e.id desc");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

}
