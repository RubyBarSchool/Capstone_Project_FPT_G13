package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestMaterialVO {
    private String groupMaterial;
    private String idMaterial;
    private String frameMaterial;
    private String unitOFMaterial;
    private String company;
    private Double percentChoose;
}
