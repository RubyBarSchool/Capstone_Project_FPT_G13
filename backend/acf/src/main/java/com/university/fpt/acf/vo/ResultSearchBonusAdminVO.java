package com.university.fpt.acf.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResultSearchBonusAdminVO {
    private Long id;
    private String title;
    private String reason;
    private String money;
    private Boolean status;
    private LocalDate effectiveDate;
    private List<Long> listIdEmployee;
}
