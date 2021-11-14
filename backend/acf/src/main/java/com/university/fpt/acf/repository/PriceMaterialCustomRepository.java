package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.form.PriceMaterialForm;

public interface PriceMaterialCustomRepository {
    PriceMaterial getPriceMaterial(PriceMaterialForm priceForm);
    PriceMaterial getPriceCoverSheet(PriceMaterialForm priceForm);
}
