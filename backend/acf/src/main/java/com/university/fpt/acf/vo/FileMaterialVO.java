package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileMaterialVO {
    private Long idMaterial;
    private String frameHeightMaterial;
    private String frameWidthMaterial;
    private String frameLengthMaterial;
    private String noteMaterial;
    private Long idCompanyMaterial;
    private Long idUnitMaterial;
    private Integer countMaterial;
    private String priceMaterial = "0";
}
