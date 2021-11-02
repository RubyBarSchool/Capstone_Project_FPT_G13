package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddAdvanceSalaryEmployeeForm {
    private String advanceSalary;
    private String title;
    private String content;
//    private List<LocalDate> date;
//    private Long idEmployee;
}
