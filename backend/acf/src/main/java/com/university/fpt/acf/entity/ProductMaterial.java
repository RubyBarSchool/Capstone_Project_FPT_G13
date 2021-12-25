package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductMaterial extends EntityCommon {

    private Integer count;

    @Column(columnDefinition = "TEXT")
    private String note;

    private String priceAtCreateContact;

    @ManyToOne(fetch =  FetchType.EAGER)
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
