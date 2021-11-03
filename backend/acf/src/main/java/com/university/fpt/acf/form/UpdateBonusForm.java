package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateBonusForm {
    private Long id;
    private String title;
    private List<Long> listIdEmployee;
    private String reason;
    private String money;
    private Boolean status;
    private LocalDate effectiveDate;
}
