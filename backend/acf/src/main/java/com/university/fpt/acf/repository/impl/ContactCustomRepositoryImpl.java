package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.repository.ContactCustomRepository;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.ContactVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContactCustomRepositoryImpl extends CommonRepository implements ContactCustomRepository {
    @Override
    public List<ContactVO> searchContact(ContactInSearchForm contactInSearchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.ContactVO(c.id,c.name) FROM Contact c where c.deleted = false ");
        if(!contactInSearchForm.getName().isEmpty() && contactInSearchForm.getName()!=null){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+contactInSearchForm.getName().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<ContactVO> query = super.createQuery(sql.toString(),params, ContactVO.class);
        query.setFirstResult((contactInSearchForm.getPageIndex()-1)* contactInSearchForm.getPageSize());
        query.setMaxResults(contactInSearchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int getTotalSearchContact(ContactInSearchForm contactInSearchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) FROM Contact c where c.deleted = false ");
        if(!contactInSearchForm.getName().isEmpty() && contactInSearchForm.getName()!=null){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+contactInSearchForm.getName().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
