package com.university.fpt.acf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.fpt.acf.common.entity.EntityCommon;
import com.university.fpt.acf.config.security.entity.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends EntityCommon {
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String countryside;
    private String email;
    private String nation;
    private String phone;
    private Long salary;
    private String image;


    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Position position;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<TimeKeep> timeKeeps;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<PersonaLeaveApplication> personaLeaveApplications;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<AdvaceSalary> advaceSalaries;

    @JsonIgnore
    @OneToMany(mappedBy = "employee",fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<HistorySalary> historySalaries;
}
