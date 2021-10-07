package com.university.fpt.acf.vo;

import com.university.fpt.acf.config.security.entity.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;


@Data
public class GetAllAccountVO {
    private Long id;
    private String username;
    private Collection<String> roles = new ArrayList<>();
    private Boolean status;
    private LocalDate time;
}
