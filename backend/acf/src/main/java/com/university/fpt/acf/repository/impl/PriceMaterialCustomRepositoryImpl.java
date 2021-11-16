package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.form.PriceMaterialForm;
import com.university.fpt.acf.repository.PriceMaterialCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PriceMaterialCustomRepositoryImpl extends CommonRepository implements PriceMaterialCustomRepository {
    @Override
    public PriceMaterial getPriceMaterial(PriceMaterialForm priceForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT p from PriceMaterial p inner join p.material m where m.checkMaterial = true and m.deleted = false and p.material.id=:idMaterial and p.unitMeasure.id=:idUnit and p.frameMaterial.id=:idFrame and p.heightMaterial.id=:idHeight ");
        params.put("idMaterial", priceForm.getId());
        params.put("idUnit", priceForm.getIdUnit());
        params.put("idFrame", priceForm.getIdFrame());
        params.put("idHeight", priceForm.getIdHeight());
        TypedQuery<PriceMaterial> query = super.createQuery(sql.toString(), params, PriceMaterial.class);
        return query.getSingleResult();
    }

    @Override
    public PriceMaterial getPriceCoverSheet(PriceMaterialForm priceForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT p from PriceMaterial p inner join p.material m where m.checkMaterial =false and m.deleted = false and p.material.id=:idMaterial and p.unitMeasure.id=:idUnit and p.frameMaterial.id=:idFrame and p.heightMaterial.id=:idHeight ");
        params.put("idMaterial", priceForm.getId());
        params.put("idUnit", priceForm.getIdUnit());
        params.put("idFrame", priceForm.getIdFrame());
        params.put("idHeight", priceForm.getIdHeight());
        TypedQuery<PriceMaterial> query = super.createQuery(sql.toString(), params, PriceMaterial.class);
        PriceMaterial p = query.getSingleResult();
        return p;
    }
}
