package com.university.fpt.acf.vo;

import com.university.fpt.acf.config.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetAccountDetailResponeVO {
    private Long id;
    private String username;
    private Collection<RoleAccountVO> roles = new ArrayList<>();
    private String image;
    private String fullname;
    private LocalDate dob;
    private String phone;
    private Boolean gender;
    private String email;
    private String address;
}
