package com.university.fpt.acf.form;

import com.university.fpt.acf.config.security.entity.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class AddAccountForm {
    //username, password,list idRole,idEmployee
    private  String username;
    private String password;
    private Collection<Long> idRole = new ArrayList<>();
    private Long id_employee;
}
