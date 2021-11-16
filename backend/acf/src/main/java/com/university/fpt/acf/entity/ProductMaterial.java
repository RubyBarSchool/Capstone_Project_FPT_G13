package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMaterial extends EntityCommon {

    private Integer count;

    private String note;

    private String priceAtCreateContact;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Product product;

    @ManyToOne
    @JoinColumn(name = "price_material_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private PriceMaterial priceMaterial;

}
