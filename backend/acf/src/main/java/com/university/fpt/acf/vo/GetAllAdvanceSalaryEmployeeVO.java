package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllAdvanceSalaryEmployeeVO {
    private Long id;
    private LocalDate dateCreate;
    private String advanceSalary;
    private String title;
    private String content;
    private String accept;
    private String comment;
    private Long idEmployeeAccept;
    private String employeeAccept;
    private LocalDate dateAccept;
}
