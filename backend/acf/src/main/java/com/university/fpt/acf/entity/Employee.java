package com.university.fpt.acf.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import com.university.fpt.acf.config.security.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends EntityCommon {
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String countryside;
    private String nation;
    private String phone;
    private Long salary;
    private String job;
    private String image;
}
