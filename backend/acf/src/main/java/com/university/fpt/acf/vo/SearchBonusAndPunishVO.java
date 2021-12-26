package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBonusAndPunishVO {
    private Long id;
    private String title;
    private String reason;
    private String money;
    private Boolean status;
    private LocalDate effectiveDate;
    private Boolean bonus;
    private String userCreate;
}
