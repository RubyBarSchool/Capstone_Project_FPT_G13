package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class UpdateColorForm {
    private Long id;
    private String name;
    private String code;
    private String image;
    private Long idCompany;
}
