package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrameMaterial extends EntityCommon {

    private String frameLength;
    private String frameWidth;


    @ManyToOne
    @JoinColumn(name = "height_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private HeightMaterial heightMaterial;

    @ManyToOne
    @JoinColumn(name = "unit_measure_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UnitMeasure unitMeasure;

    @OneToMany(mappedBy = "frameMaterial",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<PriceMaterial> priceMaterials;
}
