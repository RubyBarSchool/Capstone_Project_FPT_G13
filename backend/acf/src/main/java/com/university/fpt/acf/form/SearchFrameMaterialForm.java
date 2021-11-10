package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class SearchFrameMaterialForm {
    private String length;
    private String width;
    private Long idUnit;
    private int pageIndex;
    private int pageSize;
}
