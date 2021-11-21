package com.university.fpt.acf.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewWorkVO {
    private Long id;
    private String nameProductionOrder;
    private String nameProduct;
    private String frame;
    private Integer countProduct;
    private String numberFinish;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private Integer status;
}
