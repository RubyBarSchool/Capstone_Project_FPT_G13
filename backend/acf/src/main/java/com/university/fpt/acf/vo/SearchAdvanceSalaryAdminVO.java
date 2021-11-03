package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAdvanceSalaryAdminVO {
    private Long id;
    private String title;
    private String advanceSalary;
    private String status;
    private Long idEmployee;
    private String nameEmployee;
}
