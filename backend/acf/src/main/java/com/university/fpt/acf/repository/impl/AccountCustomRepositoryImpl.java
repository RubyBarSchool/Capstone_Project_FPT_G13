package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.repository.AccountCustomRepository;
import com.university.fpt.acf.vo.GetAllAccountVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountCustomRepositoryImpl extends CommonRepository implements AccountCustomRepository {
    @Override
    public List<GetAllAccountVO> getAllAccount(SearchAccountForm searchAccountForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select a.id from Account a left join a.roles r where a.deleted = false ");
        if(searchAccountForm.getName() != null && !searchAccountForm.getName().isEmpty()){
            sqlAcc.append(" and LOWER(a.username) like :name ");
            paramsAcc.put("name","%"+searchAccountForm.getName().toLowerCase()+"%");
        }
        if(searchAccountForm.getListStatus() != null && !searchAccountForm.getListStatus().isEmpty()){
            sqlAcc.append(" and a.status in :status ");
            paramsAcc.put("status",searchAccountForm.getListStatus());
        }
        if(searchAccountForm.getDate() != null && !searchAccountForm.getDate().isEmpty()){
            sqlAcc.append(" and  a.modified_date BETWEEN :dateStart and :dateEnd");
            paramsAcc.put("dateStart",searchAccountForm.getDate().get(0));
            paramsAcc.put("dateEnd",searchAccountForm.getDate().get(1));
        }
        if(searchAccountForm.getListRole() != null && !searchAccountForm.getListRole().isEmpty()){
            sqlAcc.append(" and r.id in :roles ");
            paramsAcc.put("roles",searchAccountForm.getListRole());
        }
        sqlAcc.append(" GROUP BY a.id ");
        sqlAcc.append(" ORDER by a.id desc ");
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(),paramsAcc, Long.class);
        queryAcc.setFirstResult((searchAccountForm.getPageIndex()-1)*searchAccountForm.getPageSize());
        queryAcc.setMaxResults(searchAccountForm.getPageSize());
        List<Long> accList = queryAcc.getResultList();

        List<GetAllAccountVO> resultList = new ArrayList<>();
        if(accList.size()!=0){
            StringBuilder sql = new StringBuilder("");
            Map<String, Object> params = new HashMap<>();
            sql.append(" select new  com.university.fpt.acf.vo.GetAllAccountVO(a.id,a.username,r.id,r.name,a.status,a.modified_date)" +
                    " from Account a left join a.roles r where  a.id in :listId ");
            params.put("listId",accList);
            sql.append(" ORDER by a.id desc ");
            TypedQuery<GetAllAccountVO> query = super.createQuery(sql.toString(),params, GetAllAccountVO.class);
            resultList = query.getResultList();
        }
        return resultList;
    }

    @Override
    public int getTotalAllAccount(SearchAccountForm searchAccountForm) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select COUNT(*) from Account a left join a.roles r where a.deleted = false ");
        if(searchAccountForm.getName() != null && !searchAccountForm.getName().isEmpty()){
            sqlAcc.append(" and LOWER(a.username) like :name ");
            paramsAcc.put("name","%"+searchAccountForm.getName().toLowerCase()+"%");
        }
        if(searchAccountForm.getListStatus() != null && !searchAccountForm.getListStatus().isEmpty()){
            sqlAcc.append(" and a.status in :status ");
            paramsAcc.put("status",searchAccountForm.getListStatus());
        }
        if(searchAccountForm.getDate() != null && !searchAccountForm.getDate().isEmpty()){
            sqlAcc.append(" and  a.modified_date BETWEEN :dateStart and :dateEnd");
            paramsAcc.put("dateStart",searchAccountForm.getDate().get(0));
            paramsAcc.put("dateEnd",searchAccountForm.getDate().get(1));
        }
        if(searchAccountForm.getListRole() != null && !searchAccountForm.getListRole().isEmpty()){
            sqlAcc.append(" and r.id in :roles ");
            paramsAcc.put("roles",searchAccountForm.getListRole());
        }
        sqlAcc.append(" GROUP BY a.id ");
        TypedQuery<Long> queryAcc = super.createQuery(sqlAcc.toString(),paramsAcc, Long.class);
        return queryAcc.getResultList().size();
    }
}
