package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileProductVO {
    private String nameProduct;
    private Integer countProduct;
    private String frameHeightProduct;
    private String frameWidthProduct;
    private String frameLengthProduct;
    private String noteProduct;
    private String priceProduct = "0";
    private List<FileMaterialVO> fileMaterialVOList = new ArrayList<>();
}
