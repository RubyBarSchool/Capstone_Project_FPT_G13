package com.university.fpt.acf.vo;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountDetailVO {
    private Long id;
    private String username;
    private Long idRole;
    private String nameRole;
    private String image;
    private String fullname;
    private LocalDate dob;
    private String phone;
    private Boolean gender;
    private String email;
    private String address;

}
