package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class  SearchPersonalApplicationEmployeeForm {
    private String title;
    private String status;
    private List<LocalDate> date;
    private Integer pageIndex;
    private Integer pageSize;
//    private Long idEmployee;
}
