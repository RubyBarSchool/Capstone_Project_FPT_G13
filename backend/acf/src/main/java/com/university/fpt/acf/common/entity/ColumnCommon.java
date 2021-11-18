package com.university.fpt.acf.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColumnCommon {
    private String title = "";
    private String dataIndex = "";
    private String key = "";
    private Integer width = 150;
    private String fixed  = "";
    private ColumnCustomCommon scopedSlots = new ColumnCustomCommon();
}
