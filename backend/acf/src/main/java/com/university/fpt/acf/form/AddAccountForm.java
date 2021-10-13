package com.university.fpt.acf.form;

import com.university.fpt.acf.config.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAccountForm {
    //username, password,list idRole,idEmployee
    private  String username;
    private String password;
    private Collection<Long> listRole = new ArrayList<>();
    private Long employee;
}
