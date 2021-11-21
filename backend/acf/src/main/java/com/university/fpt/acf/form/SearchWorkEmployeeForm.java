package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchWorkEmployeeForm {
    private Integer status;
    private int pageIndex;
    private int pageSize;
    private Integer total;
}
