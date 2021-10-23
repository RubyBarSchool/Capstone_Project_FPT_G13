package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.repository.PositionCustomRepository;
import com.university.fpt.acf.vo.GetAllRoleVO;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class PositionCustomRepositoryImpl extends CommonRepository implements PositionCustomRepository {
    @Override
    public List<PositionResponseVO>seachPosition(PositionForm positionForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select new  com.university.fpt.acf.vo.PositionResponseVO(p.id,p.name) from Position p where p.deleted = false ");
        if(positionForm.getName() != null && !positionForm.getName().isEmpty()){
            sql.append(" and LOWER(p.name) like :name ");
            params.put("name","%"+positionForm.getName().toLowerCase()+"%");
        }
        sql.append(" ORDER by p.id desc ");
        TypedQuery<PositionResponseVO> query = super.createQuery(sql.toString(),params, PositionResponseVO.class);
        query.setFirstResult((positionForm.getPageIndex()-1)* positionForm.getPageSize());
        query.setMaxResults(positionForm.getPageSize());
        return query.getResultList();

    }

    @Override
    public int totalSearchPosition(PositionForm positionForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" select COUNT(*) from Position p where p.deleted = false ");
        if(positionForm.getName() != null && !positionForm.getName().isEmpty()){
            sql.append(" and LOWER(p.name) like :name ");
            params.put("name","%"+positionForm.getName().toLowerCase()+"%");
        }
        sql.append(" ORDER by p.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
