package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupMaterial extends EntityCommon {
    private String name;

    private Boolean checkGroupMaterial = true;

    @OneToMany(mappedBy = "groupMaterial", cascade =  CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Material> materials;
}
