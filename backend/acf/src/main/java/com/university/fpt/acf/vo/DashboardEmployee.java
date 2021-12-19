package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardEmployee {
    private Double timeKeep;
    private Integer workConfirm;
    private Integer workDone;
    private Integer bonus;
    private Integer punish;
}
