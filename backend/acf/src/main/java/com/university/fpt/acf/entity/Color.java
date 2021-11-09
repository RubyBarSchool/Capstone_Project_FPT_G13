package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Color extends EntityCommon {
    private String code;

    private Double percentChooseInMonth = 0.0;

    private Double percentChooseInQuarterOfYear = 0.0;

    private Double percentChooseInYear = 0.0;

    @ManyToOne
    @JoinColumn(name = "group_color_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private GroupColor groupColor;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Material> materials;
}
