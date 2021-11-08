package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBonusAdminVO {
    private Long id;
    private String title;
    private String reason;
    private String money;
    private Boolean status;
    private LocalDate effectiveDate;
    private Long idEmployee;
    private String nameEmployee;
}
