package com.university.fpt.acf.vo;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
@Data
public class GetAccountDetailVO {
    private String username;
    private Collection<String> roleName = new ArrayList<>();
    private String image;
    private  LocalDate dob;
    private String phone;
    private String email;
    private String gender;

}
