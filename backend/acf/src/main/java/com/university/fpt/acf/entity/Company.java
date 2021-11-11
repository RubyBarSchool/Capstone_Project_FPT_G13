package com.university.fpt.acf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends EntityCommon {
    private String name;
    private String address;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Material> materials;

    @JsonIgnore
    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Contact> contacts;
}
