package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SearchAccountForm {
    private String name;
    private List<Long> listRole;
    private List<Boolean> listStatus;
    private List<LocalDate> date;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer total;
}
