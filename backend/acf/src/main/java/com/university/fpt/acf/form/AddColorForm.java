package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class AddColorForm {
    private String code;
    private String name;
    private String image;
    private Long idCompany;
}
