package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorySalary extends EntityCommon {

    private LocalDate dateAccept;
    private Double countWork;
    private String salary;
    private String bonus;
    private String penalty;
    private String advanceSalary;
    private String totalMoney;
    private Boolean status = false;
    private String accountAccept;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Employee employee;
}
