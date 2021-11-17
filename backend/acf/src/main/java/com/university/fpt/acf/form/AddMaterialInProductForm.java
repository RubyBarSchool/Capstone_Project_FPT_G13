package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMaterialInProductForm {
    private Integer count;
    private Long id;
    private String note;
    private String price;
}
