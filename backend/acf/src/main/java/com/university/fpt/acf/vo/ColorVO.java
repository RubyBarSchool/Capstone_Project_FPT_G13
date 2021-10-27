package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColorVO {
    private Long id;
    private String code;
    private String name;
    private String image;
    private Long idCompany;
    private String nameCompany;
}
