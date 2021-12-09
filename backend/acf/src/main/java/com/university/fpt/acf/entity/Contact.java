package com.university.fpt.acf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contact extends EntityCommon {
    private String name;

    private LocalDate dateFinish;

    private String totalMoney;

    @Column(columnDefinition = "TEXT")
    private String note;

    private String numberFinish;

    private Integer statusDone = -2;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Company company;

    @OneToMany(mappedBy = "contact", cascade =  CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Product> products;

    @JsonIgnore
    @OneToMany(mappedBy = "contact",cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<ContactMoney> contactMonies;

}
