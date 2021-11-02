package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class UpdateAdvanceSalaryEmployeeForm {
    private Long id;
    private String advanceSalary;
    private String title;
    private String content;
    //    private List<LocalDate> date;
//    private Long idEmployee;
}
