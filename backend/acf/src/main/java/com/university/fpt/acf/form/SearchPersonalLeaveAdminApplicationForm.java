package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchPersonalLeaveAdminApplicationForm {
    private String status;
    private String title;
    private String nameEmployee;
    private List<LocalDate> date;
    private Integer pageIndex;
    private Integer pageSize;
}
