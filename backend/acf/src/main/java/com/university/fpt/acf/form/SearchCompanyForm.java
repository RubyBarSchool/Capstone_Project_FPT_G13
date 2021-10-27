package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCompanyForm {
    private String name;
    private String address;
    private String phone;
    private Integer pageIndex;
    private Integer pageSize;
}
