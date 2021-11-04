package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailAdvanceSalaryAdminVO {
    private Long id;
    private LocalDate date;
    private String title;
    private String advanceSalary;
    private String status;
    private Long idEmployee;
    private String nameEmployee;
    private String content;
}
