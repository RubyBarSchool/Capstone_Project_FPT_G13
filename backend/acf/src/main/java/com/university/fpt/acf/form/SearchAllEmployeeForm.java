package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class SearchAllEmployeeForm {
    private String name;
    private Long idPosition;
    private Boolean statusDelete;
    private Integer pageIndex;
    private Integer pageSize;

}
