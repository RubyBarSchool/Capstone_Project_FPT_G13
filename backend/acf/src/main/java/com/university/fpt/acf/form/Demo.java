package com.university.fpt.acf.form;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Demo {
    private String username;
    private List<Long> listIdRole= new ArrayList<>();
    private Boolean status;
    private Integer sort;
}
