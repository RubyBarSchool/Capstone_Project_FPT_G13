package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class SearchAdvanceSalaryAdminForm {
    private String title;
    private String employeeName;
    private Boolean status;
    private Integer pageIndex;
    private Integer pageSize;
}
