package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionOrderViewWorkVO {
    private Long idEmployee;
    private String nameEmployee;
    private Long idProductionOrder;
    private String nameProductionOrder;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    public ProductionOrderViewWorkVO(Long idEmployee, String nameEmployee) {
        this.idEmployee = idEmployee;
        this.nameEmployee = nameEmployee;
    }
}
