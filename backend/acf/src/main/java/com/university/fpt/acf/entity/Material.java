package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Material extends EntityCommon {

    private String name;

    private Boolean checkMaterial = true;

    private Double percentChooseInMonth = 0.0;

    private Double percentChooseInQuarterOfYear = 0.0;

    private Double percentChooseInYear = 0.0;

    @ManyToOne
    @JoinColumn(name = "unit_measure_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UnitMeasure unitMeasure;


    @ManyToOne
    @JoinColumn(name = "company_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Company company;


    @OneToMany(mappedBy = "material",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<PriceMaterial> priceMaterials;



}
