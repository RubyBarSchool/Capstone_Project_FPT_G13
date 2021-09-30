package com.university.fpt.acf.common.entity;

import lombok.Data;

@Data
public class ResponseCommon {
    private Object data;
    private String message;
    private Integer status;
}
