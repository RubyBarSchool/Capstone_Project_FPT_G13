package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdatePersonalAppEmployeeForm {
    private Long idApplication;
    private String fileAttach;
    private String title;
    private String content;
    private List<LocalDate> date;

}
