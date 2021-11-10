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


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "frame_height_material",joinColumns = @JoinColumn(name = "frame_material_id")
            ,inverseJoinColumns = @JoinColumn(name = "height_material_id"))
    private Collection<HeightMaterial> heightMaterial;

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
