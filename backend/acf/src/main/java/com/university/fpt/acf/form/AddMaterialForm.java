package com.university.fpt.acf.form;

import lombok.Data;

import java.util.List;

@Data
public class AddMaterialForm {
    private List<String> name;
    private List<Long> listIdFrame;
}
