package com.university.fpt.acf.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeDetailVO {
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String address;
    private String email;
    private String nation;
    private String phone;
    private Long salary;
    private String image;
    private Long idPosition;
    private String positionName;
}
