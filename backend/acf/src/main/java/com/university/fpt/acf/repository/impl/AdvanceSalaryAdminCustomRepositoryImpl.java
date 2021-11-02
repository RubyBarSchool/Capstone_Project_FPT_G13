package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchAdvanceSalaryAdminForm;
import com.university.fpt.acf.repository.AdvanceSalaryAdminCustomRepository;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdvanceSalaryAdminCustomRepositoryImpl extends CommonRepository implements AdvanceSalaryAdminCustomRepository {
    @Override
    public List<SearchAdvanceSalaryAdminVO> searchAdvanceSalary(SearchAdvanceSalaryAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO(a.id,a.title,a.advaceSalary,a.accept,e.id,e.fullName) from AdvaceSalary a inner join a.employee  e where a.deleted=false");
        if(!searchForm.getTitle().isEmpty() && searchForm.getTitle()!=null){
            sql.append(" and LOWER(a.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if(!searchForm.getEmployeeName().isEmpty() && searchForm.getEmployeeName()!=null){
            sql.append(" and LOWER(e.fullName) like :content ");
            params.put("content","%"+searchForm.getEmployeeName().toLowerCase()+"%");
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and LOWER(a.accept) =:status ");
            params.put("status",searchForm.getStatus());
        }
//        if (advanceForm.getDate() != null && !advanceForm.getDate().isEmpty()) {
//            sql.append(" and  a.date BETWEEN :dateStart and :dateEnd ");
//            params.put("dateStart", advanceForm.getDate().get(0));
//            params.put("dateEnd", advanceForm.getDate().get(1));
//        }
        sql.append(" ORDER by a.id desc ");
        TypedQuery<SearchAdvanceSalaryAdminVO> query = super.createQuery(sql.toString(),params, SearchAdvanceSalaryAdminVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchAdvance(SearchAdvanceSalaryAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select COUNT(*) from AdvaceSalary a inner join a.employee  e where a.deleted=false");
        if(!searchForm.getTitle().isEmpty() && searchForm.getTitle()!=null){
            sql.append(" and LOWER(a.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if(!searchForm.getEmployeeName().isEmpty() && searchForm.getEmployeeName()!=null){
            sql.append(" and LOWER(e.fullName) like :content ");
            params.put("content","%"+searchForm.getEmployeeName().toLowerCase()+"%");
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and LOWER(a.accept) =:status ");
            params.put("status",searchForm.getStatus());
        }
//        if (advanceForm.getDate() != null && !advanceForm.getDate().isEmpty()) {
//            sql.append(" and  a.date BETWEEN :dateStart and :dateEnd ");
//            params.put("dateStart", advanceForm.getDate().get(0));
//            params.put("dateEnd", advanceForm.getDate().get(1));
//        }
        sql.append(" ORDER by a.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}

