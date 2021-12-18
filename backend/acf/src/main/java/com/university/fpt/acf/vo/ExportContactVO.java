package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportContactVO {
    private String nameContact;
    private LocalDate dateFinish;
    private String note;
    private String numberFinish;
    private String totalMoney;
    private Integer statusContact;

    private Long idProduct;
    private String nameProduct;
    private Integer statusProduct;
    private Integer countProduct;
    private String widthProduct;
    private String heightProduct;
    private String lengthProduct;
    private String noteProduct;
    private String priceProduct;
    private String priceInContact;

    private Integer countMaterialInProduct;
    private String noteMaterialInProduct;
    private String priceAtCreateContact;

    private String priceMaterial;
    private String nameUnit;
    private String nameMaterial;
    private String frameLength;
    private String frameWidth;
    private String frameHeight;

    private String companyName;
}
