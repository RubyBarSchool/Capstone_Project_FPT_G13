package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchCreateContactFrom {
    private String name;
    private List<LocalDate> listDate;
    private List<Long> listIdCompany;
    private int pageIndex;
    private int pageSize;
}
