package com.university.fpt.acf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Collection;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position extends EntityCommon {
    private String code;
    private String name;

    @OneToMany(mappedBy = "position",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<Employee> employees;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<HistorySalary> historySalaries;
}
