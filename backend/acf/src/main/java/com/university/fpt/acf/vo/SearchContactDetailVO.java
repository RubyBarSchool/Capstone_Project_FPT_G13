package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchContactDetailVO {
    private Long idContact;
    private Long idProduct;
    private String nameContact;
    private String nameProduct;
    private String frame;
    private Integer count;
    private String note;
    private String price;
    private Integer status;
}
