package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailEmployeeInformationVO {
    private String image;
    private String fullName;
    private Boolean gender;
    private String nation;
    private LocalDate dob;
    private String email;
    private String positionName;
    private String phone;
    private String address;
    private Long salary;
    private String username;
    private String roleName;
}
