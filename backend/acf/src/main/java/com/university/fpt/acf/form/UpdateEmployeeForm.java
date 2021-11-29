package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeForm {
    private Long id;
    @NotNull(message = "image đang null")
    private String image;
    @NotNull(message = "Tên đang null")
    @NotEmpty(message = "Tên đang để chống")
    private String fullName;
    private Boolean gender;
    private LocalDate dob;
    @NotNull(message = "email đang null")
    private String email;
    @Min(value = 10,message = "SĐT phải có 10 số") private String phone;
    @NotNull(message = "Địa chỉ đang null")
    private String address;
    @NotNull(message = "dân tộc đang null")
    private String nation;
    @NotNull(message = "Lương đang null")
    private Long salary;
    @NotNull(message = "idPosition đang null")
    private Long idPosition;
}
