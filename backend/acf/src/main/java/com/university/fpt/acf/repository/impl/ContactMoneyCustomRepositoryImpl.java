package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.entity.ContactMoney;
import com.university.fpt.acf.form.SearchContactMoneyForm;
import com.university.fpt.acf.repository.ContactMoneyCustomRepository;
import com.university.fpt.acf.vo.ContactVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ContactMoneyCustomRepositoryImpl extends CommonRepository implements ContactMoneyCustomRepository {
    @Override
    public List<ContactMoney> searchContactMoney(SearchContactMoneyForm searchContactMoneyForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select cm  from ContactMoney cm inner  join cm.contact c where cm.deleted = false  ");
        if(searchContactMoneyForm.getNameContact()!=null && !searchContactMoneyForm.getNameContact().isEmpty()){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+searchContactMoneyForm.getNameContact().toLowerCase()+"%");
        }
        if(searchContactMoneyForm.getStatusDone()!=null && searchContactMoneyForm.getStatusDone() != 0){
            sql.append(" and c.statusDone = :statusDone ");
            params.put("statusDone",searchContactMoneyForm.getStatusDone());
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<ContactMoney> query = super.createQuery(sql.toString(),params, ContactMoney.class);
        query.setFirstResult((searchContactMoneyForm.getPageIndex()-1)* searchContactMoneyForm.getPageSize());
        query.setMaxResults(searchContactMoneyForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int getTotalsearchContactMoney(SearchContactMoneyForm searchContactMoneyForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select COUNT(*)  from ContactMoney cm inner  join cm.contact c where cm.deleted = false  ");
        if(searchContactMoneyForm.getNameContact()!=null && !searchContactMoneyForm.getNameContact().isEmpty()){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+searchContactMoneyForm.getNameContact().toLowerCase()+"%");
        }
        if(searchContactMoneyForm.getStatusDone()!=null && searchContactMoneyForm.getStatusDone() != 0){
            sql.append(" and c.statusDone = :statusDone ");
            params.put("statusDone",searchContactMoneyForm.getStatusDone());
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
