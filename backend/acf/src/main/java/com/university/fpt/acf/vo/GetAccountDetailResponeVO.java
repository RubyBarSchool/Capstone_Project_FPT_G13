package com.university.fpt.acf.vo;

import com.university.fpt.acf.config.security.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class GetAccountDetailResponeVO {
    private Long id;
    private String username;
    private Collection<RoleAccountVO> roles = new ArrayList<>();
    private String image;
    private String fullname;
    private LocalDate dob;
    private String phone;
    private Boolean gender;
}
