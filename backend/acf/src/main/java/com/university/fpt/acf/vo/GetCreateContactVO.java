package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCreateContactVO {
    private Long id;
    private String name;
    private LocalDate createDate;
    private LocalDate dateFinish;
    private Long idCompany;
    private String nameCompany;
    private String totalMoney;
    private String numberFinish;
    private Integer statusDone;
    private String note;
}
