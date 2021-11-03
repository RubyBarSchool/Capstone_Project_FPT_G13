package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BonusPenalty extends EntityCommon {
    private String title;
    private String reason;
    private String money;
    private String status;
    private LocalDate effectiveDate;
    private Boolean bonus;


    @ManyToMany(mappedBy = "bonusPenalties",fetch = FetchType.EAGER)
    private Collection<Employee> employees = new ArrayList<>();


}
