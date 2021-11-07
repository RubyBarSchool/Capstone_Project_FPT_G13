package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.RolesForm;
import com.university.fpt.acf.repository.RolesCustomRepository;
import com.university.fpt.acf.vo.GetAllRoleVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RolesCustomRepositoryImpl extends CommonRepository implements RolesCustomRepository {
    @Override
    public List<GetAllRoleVO> getRoles(RolesForm rolesForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new  com.university.fpt.acf.vo.GetAllRoleVO(r.id,r.name) from Role r ");
        if(rolesForm.getName() != null && !rolesForm.getName().isEmpty()){
            sql.append(" where LOWER(r.name) like :name ");
            params.put("name","%"+rolesForm.getName().toLowerCase()+"%");
        }
        sql.append(" ORDER by r.id desc ");
        TypedQuery<GetAllRoleVO> query = super.createQuery(sql.toString(),params, GetAllRoleVO.class);
        query.setFirstResult((rolesForm.getPageIndex()-1)* rolesForm.getPageSize());
        query.setMaxResults(rolesForm.getPageSize());
        List<GetAllRoleVO> roleList = query.getResultList();
        return roleList;
    }

    @Override
    public int totalGetRoles(RolesForm rolesForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select COUNT(*) from Role r ");
        if(rolesForm.getName() != null && !rolesForm.getName().isEmpty()){
            sql.append(" where LOWER(r.name) like :name ");
            params.put("name","%"+rolesForm.getName().toLowerCase()+"%");
        }
        sql.append(" ORDER by r.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
