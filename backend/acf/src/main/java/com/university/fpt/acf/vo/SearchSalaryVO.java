package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSalaryVO {
    private Long id;
    private LocalDate date;
    private String nameEmployee;
    private String namePosition;
    private Double countWork;
    private String salary;
    private String bonus;
    private String penalty;
    private String advanceSalary;
    private String totalMoney;
    private Boolean status;
    private String accountAccept;
    private LocalDate acceptDate;
}
