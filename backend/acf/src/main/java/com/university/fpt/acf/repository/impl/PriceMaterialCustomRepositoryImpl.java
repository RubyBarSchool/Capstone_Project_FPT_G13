package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.repository.PriceMaterialCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PriceMaterialCustomRepositoryImpl extends CommonRepository implements PriceMaterialCustomRepository {
    @Override
    public PriceMaterial getPriceMaterial(Long idParameter) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT p from PriceMaterial p inner join p.material m where m.checkMaterial = true and m.deleted = false and p.id=:id ");
        params.put("id", idParameter);
        TypedQuery<PriceMaterial> query = super.createQuery(sql.toString(), params, PriceMaterial.class);
        return query.getSingleResult();
    }

    @Override
    public PriceMaterial getPriceCoverSheet(Long idParameter) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT p from PriceMaterial p inner join p.material m where m.checkMaterial = false and m.deleted = false and p.id=:id ");
        params.put("id", idParameter);
        TypedQuery<PriceMaterial> query = super.createQuery(sql.toString(), params, PriceMaterial.class);
        PriceMaterial p = query.getSingleResult();
        return p;
    }
}
