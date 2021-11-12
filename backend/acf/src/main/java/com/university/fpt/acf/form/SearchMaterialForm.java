package com.university.fpt.acf.form;

import lombok.Data;

import java.util.List;

@Data
public class SearchMaterialForm {
    private String codeMaterial;
    private List<Long> listGroupID;
    private Long unitId;
    private List<Long> listIdCompany;
    private int pageIndex;
    private int pageSize;
}
