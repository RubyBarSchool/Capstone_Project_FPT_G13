package com.university.fpt.acf.form;

import lombok.Data;

import java.util.List;

@Data
public class SearchMaterialForm {
    private String codeMaterial;
    private String frame;
    private List<Long> listGroupID;
    private List<Long> listUnitId;
    private List<Long> listIdCompany;
    private int pageIndex;
    private int pageSize;
}
