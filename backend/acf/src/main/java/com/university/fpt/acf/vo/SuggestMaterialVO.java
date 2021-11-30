package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestMaterialVO {
    private String groupMaterial;
    private Long idMaterial;
    private String nameMaterial;
    private String company;
    private Double percentChoose;
    private String image;
}
