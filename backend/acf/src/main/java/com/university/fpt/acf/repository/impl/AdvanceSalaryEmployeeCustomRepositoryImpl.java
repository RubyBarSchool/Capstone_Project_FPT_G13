package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.AddAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.repository.AdvanceSalaryEmployeeCustomRepository;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AdvanceSalaryEmployeeCustomRepositoryImpl extends CommonRepository implements AdvanceSalaryEmployeeCustomRepository {
    @Override
    public List<GetAllAdvanceSalaryEmployeeVO> searchAdvanceSalary(SearchAdvanceEmployeeForm advanceForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO(a.id,a.advaceSalary,a.title,a.content,a.accept) from AdvaceSalary a where a.employee.id=:id");
        params.put("id",advanceForm.getIdEmployee());
        if(!advanceForm.getTitle().isEmpty() && advanceForm.getTitle()!=null){
            sql.append(" and LOWER(a.title) like :title ");
            params.put("title","%"+advanceForm.getTitle().toLowerCase()+"%");
        }
        if(!advanceForm.getContent().isEmpty() && advanceForm.getContent()!=null){
            sql.append(" and LOWER(a.content) like :content ");
            params.put("content","%"+advanceForm.getContent().toLowerCase()+"%");
        }
        if(advanceForm.getStatus()!=null){
            sql.append(" and LOWER(a.accept) =:status ");
            params.put("status",advanceForm.getStatus());
        }
//        if (advanceForm.getDate() != null && !advanceForm.getDate().isEmpty()) {
//            sql.append(" and  a.date BETWEEN :dateStart and :dateEnd ");
//            params.put("dateStart", advanceForm.getDate().get(0));
//            params.put("dateEnd", advanceForm.getDate().get(1));
//        }
        sql.append(" ORDER by a.id desc ");
        TypedQuery<GetAllAdvanceSalaryEmployeeVO> query = super.createQuery(sql.toString(),params, GetAllAdvanceSalaryEmployeeVO.class);
        query.setFirstResult((advanceForm.getPageIndex()-1)* advanceForm.getPageSize());
        query.setMaxResults(advanceForm.getPageSize());
        return query.getResultList();
    }


    @Override
    public int totalSearch(SearchAdvanceEmployeeForm advanceForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select COUNT(*) from AdvaceSalary a where 1=1");
        if(!advanceForm.getTitle().isEmpty() && advanceForm.getTitle()!=null){
            sql.append(" and LOWER(a.title) like :title ");
            params.put("title","%"+advanceForm.getTitle().toLowerCase()+"%");
        }
        if(!advanceForm.getContent().isEmpty() && advanceForm.getContent()!=null){
            sql.append(" and LOWER(a.content) like :content ");
            params.put("content","%"+advanceForm.getContent().toLowerCase()+"%");
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
