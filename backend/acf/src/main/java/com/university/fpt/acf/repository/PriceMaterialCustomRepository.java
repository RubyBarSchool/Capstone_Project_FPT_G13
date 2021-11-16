package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.form.PriceMaterialForm;

public interface PriceMaterialCustomRepository {
    PriceMaterial getPriceMaterial(Long idParameter);
    PriceMaterial getPriceCoverSheet(Long idParameter);
}
