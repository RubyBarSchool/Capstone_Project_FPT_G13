package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchContactDetailForm {
    private List<Long> idContact;
    private String nameProduct;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer total;
}
