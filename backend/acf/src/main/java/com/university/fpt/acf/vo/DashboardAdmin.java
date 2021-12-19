package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardAdmin {
    private Integer productNotProductionOrder = 0;
    private Integer contactDone  = 0;
    private Integer producttionOrderConfirmAdmin  = 0;
    private Integer producttionOrderConfirmEmployee  = 0;
    private Integer personaLeaveApplicationConfirm  = 0;
    private Integer advaceSalaryConfirm  = 0;
}
