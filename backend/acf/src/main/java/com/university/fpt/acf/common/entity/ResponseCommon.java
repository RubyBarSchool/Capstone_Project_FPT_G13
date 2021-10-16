package com.university.fpt.acf.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCommon {
    private Object data;
    private Integer total;
    private String message;
    private Integer status;
}
