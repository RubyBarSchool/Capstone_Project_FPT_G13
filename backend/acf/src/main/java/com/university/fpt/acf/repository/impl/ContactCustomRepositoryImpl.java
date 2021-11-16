package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.form.SearchContactDetailForm;
import com.university.fpt.acf.repository.ContactCustomRepository;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.SearchContactDetailVO;
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

    @Override
    public List<SearchContactDetailVO> searchContactDetail(SearchContactDetailForm searchContactDetailForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.SearchContactDetailVO(c.id,p.id,c.name,p.name," +
                "CONCAT(p.length,'x',p.width,'x',p.height),p.count,p.note,p.price,p.status) FROM Contact c " +
                " inner join c.products p where c.deleted = false and p.deleted = false ");
        if(searchContactDetailForm.getIdContact() != null && searchContactDetailForm.getIdContact().size() !=0){
            sql.append(" and c.id in :id ");
            params.put("id",searchContactDetailForm.getIdContact());
        }
        if(!searchContactDetailForm.getNameProduct().isEmpty() && searchContactDetailForm.getNameProduct()!=null){
            sql.append(" and LOWER(p.name) like :name ");
            params.put("name","%"+searchContactDetailForm.getNameProduct().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<SearchContactDetailVO> query = super.createQuery(sql.toString(),params, SearchContactDetailVO.class);
        query.setFirstResult((searchContactDetailForm.getPageIndex()-1)* searchContactDetailForm.getPageSize());
        query.setMaxResults(searchContactDetailForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int getTotalSearchContactDetail(SearchContactDetailForm searchContactDetailForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) FROM Contact c  inner join c.products p where c.deleted = false and p.deleted = false ");
        if(searchContactDetailForm.getIdContact() != null && searchContactDetailForm.getIdContact().size() !=0){
            sql.append(" and c.id in :id ");
            params.put("id",searchContactDetailForm.getIdContact());
        }
        if(!searchContactDetailForm.getNameProduct().isEmpty() && searchContactDetailForm.getNameProduct()!=null){
            sql.append(" and LOWER(p.name) like :name ");
            params.put("name","%"+searchContactDetailForm.getNameProduct().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
