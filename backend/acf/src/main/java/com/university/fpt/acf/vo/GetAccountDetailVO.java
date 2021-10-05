package com.university.fpt.acf.vo;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
@Data
public class GetAccountDetailVO {
    private Long id;
    private String username;
    private Collection<Role> roles = new ArrayList<>();
    private Boolean status;
    private LocalDate time;
    private Employee employee;

}
