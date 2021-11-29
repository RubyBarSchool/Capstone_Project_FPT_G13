package com.university.fpt.acf.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.university.fpt.acf.common.entity.EntityCommon;
import com.university.fpt.acf.config.security.entity.Account;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends EntityCommon {
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String address;
    private String email;
    private String nation;
    private String phone;
    private Long salary;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File image;



    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Position position;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<TimeKeep> timeKeeps;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<PersonaLeaveApplication> personaLeaveApplications;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<AdvaceSalary> advaceSalaries;

    @JsonIgnore
    @OneToMany(mappedBy = "employee")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<HistorySalary> historySalaries;




    @ManyToMany(mappedBy = "employees")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<BonusPenalty> bonusPenalties;


    @ManyToMany(mappedBy = "employees")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<ProductionOrder> productionOrders;

}
