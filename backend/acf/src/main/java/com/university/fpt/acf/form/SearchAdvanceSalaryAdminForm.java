package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchAdvanceSalaryAdminForm {
    private String title;
    private String employeeName;
    private List<LocalDate> date;
    private String status;
    private Integer pageIndex;
    private Integer pageSize;
}
