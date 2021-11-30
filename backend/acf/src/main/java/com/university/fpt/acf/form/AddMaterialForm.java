package com.university.fpt.acf.form;

import lombok.Data;

import java.util.List;

@Data
public class AddMaterialForm {
    private List<String> listName;
    private List<Long> listIdFrame;
    private List<Long> listIdHeight;
    private Long idUnit;
    private Long idGroup;
    private Long idCompany;
    private String price;
    private String image;
}
