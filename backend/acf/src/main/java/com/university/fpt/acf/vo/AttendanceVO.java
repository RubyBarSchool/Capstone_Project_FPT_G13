package com.university.fpt.acf.vo;

import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceVO {
    private Long id;
    private LocalDate date;
    private Long idEmpl;
    private String nameEmpl;
    private String type;
    private String note;


}
