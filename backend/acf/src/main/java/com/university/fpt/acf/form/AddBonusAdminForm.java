package com.university.fpt.acf.form;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AddBonusAdminForm {
    private String title;
    private List<Long> listIdEmployee;
    private String reason;
    private String money;
    private int status;
    private boolean bonus;
    private LocalDate effectiveDate;
}
