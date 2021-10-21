package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddEmployeeForm {
    private String image;
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    private String email;
    private String phone;
    private String address;
    private String nation;
    private Long salary;
    private Long idPosition;
}
