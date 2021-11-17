package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialInContactDetailVO {
    private Long idMaterial;
    private String nameMaterial;
    private String frame;
    private String nameGroupMaterial;
    private String unitMaterial;
    private String nameCompany;
    private String price;
    private String priceInContact;
    private String note;
    private Integer count;

    public MaterialInContactDetailVO(Long idMaterial, String nameMaterial, String frame, String nameGroupMaterial, String unitMaterial, String nameCompany, String price) {
        this.idMaterial = idMaterial;
        this.nameMaterial = nameMaterial;
        this.frame = frame;
        this.nameGroupMaterial = nameGroupMaterial;
        this.unitMaterial = unitMaterial;
        this.nameCompany = nameCompany;
        this.price = price;
    }
}
