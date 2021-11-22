package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceMaterial extends EntityCommon {

    private String price = "0";


    @ManyToOne
    @JoinColumn(name = "unit_measure_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UnitMeasure unitMeasure;

    @ManyToOne
    @JoinColumn(name = "material_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Material material;

    @ManyToOne
    @JoinColumn(name = "frame_material_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private FrameMaterial frameMaterial;

    @ManyToOne
    @JoinColumn(name = "frame_height_material_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HeightMaterial heightMaterial;

    @OneToMany(mappedBy = "priceMaterial",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<ProductMaterial> productMaterials;
}
