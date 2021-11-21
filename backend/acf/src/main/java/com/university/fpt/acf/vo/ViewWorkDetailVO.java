package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewWorkDetailVO {
    private Long idMaterial;
    private String nameMaterial;
    private String frameMaterial;
    private String nameGroupMaterial;
    private Integer count;
    private String unitMaterial;
    private String nameCompany;
    private String note;
}
