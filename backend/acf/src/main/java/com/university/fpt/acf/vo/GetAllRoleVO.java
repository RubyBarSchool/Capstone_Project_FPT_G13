package com.university.fpt.acf.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class GetAllRoleVO {
    private Long id;
    private String code;
    private String name;
    private LocalDate time;
}
