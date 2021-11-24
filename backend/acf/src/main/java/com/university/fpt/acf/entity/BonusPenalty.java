package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonusPenalty extends EntityCommon {

    private String title;
    @Column(columnDefinition = "TEXT")
    private String reason;
    private String money;
    private Boolean status;
    private LocalDate effectiveDate;
    private Boolean bonus;


    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "employee_bonus_penalties",
            joinColumns = @JoinColumn(name = "bonus_penalty_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Collection<Employee> employees;


}
