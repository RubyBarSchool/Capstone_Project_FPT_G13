package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchAdvanceSalaryAdminVO {
    private Long id;
    private LocalDate date;
    private String title;
    private String advanceSalary;
    private String status;
    private Long idEmployee;
    private String nameEmployee;
    private String content;
    private String comment;
    private  LocalDate dateAccept;
}
