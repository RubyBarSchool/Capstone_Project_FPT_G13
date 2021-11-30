package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialVO {
    private Long id;
    private String name;
    private Long idParameter;
    private String parameter;
    private Long idGroup;
    private String nameGroup;
    private Long idCompany;
    private String company;
    private Long idUnit;
    private String unit;
    private String price;
    private String image;
}
