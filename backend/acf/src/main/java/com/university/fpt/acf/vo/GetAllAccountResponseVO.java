package com.university.fpt.acf.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class GetAllAccountResponseVO {
    private Long id;
    private String username;
    private Collection<RoleAccountVO> roles = new ArrayList<>();
    private Boolean status;
    private LocalDate time;
}
