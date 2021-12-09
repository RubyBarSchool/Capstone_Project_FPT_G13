package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends EntityCommon {
    private String name;

    private Integer status = -2;

    private Integer count;

    private String width;

    private String height;

    private String length;
    
    private String note;

    private String price;

    private String priceInContact;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Contact contact;


    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<ProductMaterial> productMaterials;

    @OneToOne(mappedBy = "products",cascade = CascadeType.ALL)
    private ProductionOrder productionOrder;
}
