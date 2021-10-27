package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class SearchColorForm {
    private String code;
    private String name;
    private String nameCompany;
    private Integer pageIndex;
    private Integer pageSize;
}
