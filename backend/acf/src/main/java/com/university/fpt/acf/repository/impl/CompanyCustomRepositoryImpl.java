package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchCompanyForm;
import com.university.fpt.acf.repository.CompanyCustomRepository;
import com.university.fpt.acf.vo.CompanyVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class CompanyCustomRepositoryImpl extends CommonRepository implements CompanyCustomRepository {
    @Override
    public List<CompanyVO> searchCompany(SearchCompanyForm searchCompanyForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.CompanyVO(c.id,c.name,c.address,c.email,c.phone) FROM Company c where c.deleted = false");
        if(!searchCompanyForm.getName().isEmpty() && searchCompanyForm.getName()!=null){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+searchCompanyForm.getName().toLowerCase()+"%");
        }
        if(!searchCompanyForm.getAddress().isEmpty() && searchCompanyForm.getAddress()!=null){
            sql.append(" and LOWER(c.address) like :address ");
            params.put("address","%"+searchCompanyForm.getAddress().toLowerCase()+"%");
        }
        if(!searchCompanyForm.getPhone().isEmpty() && searchCompanyForm.getPhone()!=null){
            sql.append(" and LOWER(c.phone) like :phone ");
            params.put("phone","%"+searchCompanyForm.getPhone().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<CompanyVO> query = super.createQuery(sql.toString(),params, CompanyVO.class);
        query.setFirstResult((searchCompanyForm.getPageIndex()-1)* searchCompanyForm.getPageSize());
        query.setMaxResults(searchCompanyForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int getTotalSearchCompany(SearchCompanyForm searchCompanyForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) FROM Company c where c.deleted = false");
        if(!searchCompanyForm.getName().isEmpty() && searchCompanyForm.getName()!=null){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+searchCompanyForm.getName().toLowerCase()+"%");
        }
        if(!searchCompanyForm.getAddress().isEmpty() && searchCompanyForm.getAddress()!=null){
            sql.append(" and LOWER(c.address) like :address ");
            params.put("address","%"+searchCompanyForm.getAddress().toLowerCase()+"%");
        }
        if(!searchCompanyForm.getPhone().isEmpty() && searchCompanyForm.getPhone()!=null){
            sql.append(" and LOWER(c.phone) like :phone ");
            params.put("phone","%"+searchCompanyForm.getPhone().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }


}
