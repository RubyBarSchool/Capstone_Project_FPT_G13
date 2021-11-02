package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddPerLeaveAppEmployeeForm {
    private String fileAttach;
    private String title;
    private String content;
    private List<LocalDate> date;
}
