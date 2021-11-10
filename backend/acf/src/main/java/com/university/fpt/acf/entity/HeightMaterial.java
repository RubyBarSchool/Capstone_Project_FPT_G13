package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeightMaterial extends EntityCommon {
    private String height;

    @ManyToMany(mappedBy = "heightMaterial")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<FrameMaterial> frameMaterials;
}
