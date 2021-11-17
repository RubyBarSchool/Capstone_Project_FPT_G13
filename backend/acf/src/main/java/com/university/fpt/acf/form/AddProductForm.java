package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductForm {
    private Integer countProduct;
    private String heightFrame;
    private String lengthFrame;
    private Long idContact;
    private Long idProduct;
    private String nameProduct;
    private String noteProduct;
    private String priceProduct;
    private String widthFrame;
    private List<AddMaterialInProductForm> materials;
}
