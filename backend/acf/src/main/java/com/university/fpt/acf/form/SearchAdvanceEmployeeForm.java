package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class SearchAdvanceEmployeeForm {
    private String title;
    private String content;
    private String accept;
    private List<LocalDate> date;
    private Integer pageIndex;
    private Integer pageSize;
}
