package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailAdvanceSalaryEmployeeVO {
    private String title;
    private String advanceSalary;
    private String comment;
    private String content;
    private String employeeAccept;
}
