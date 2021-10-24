package com.university.fpt.acf.config.security.entity;

import com.university.fpt.acf.common.entity.EntityCommon;
import com.university.fpt.acf.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends EntityCommon {
    private Boolean status = true;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Employee employee;
}
