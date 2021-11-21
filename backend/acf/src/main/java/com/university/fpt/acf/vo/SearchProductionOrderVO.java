package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductionOrderVO {
    private Long id;
    private String name;
    private LocalDate createDate;
    private Long idContact;
    private String nameContact;
    private Long idProduct;
    private String nameProduct;
    private Integer count;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer status;
    private String numberFinish;
}
