package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactInSearchForm {
    private String name;
    private Integer pageIndex;
    private Integer pageSize;
    private Integer total;
}
