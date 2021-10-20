package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceFrom {
    private String name;
    private List<LocalDate> date;
    private String type;
    private String note;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer total;
}
