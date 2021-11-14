package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class UpdateMaterialForm {
    private Long id;
    private String price;
    private Long idFrame;
    private Long idHeight;
    private Long idUnit;
}