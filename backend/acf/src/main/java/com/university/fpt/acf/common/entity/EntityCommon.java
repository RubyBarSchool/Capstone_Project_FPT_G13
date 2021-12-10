package com.university.fpt.acf.common.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@Data
@MappedSuperclass
public class EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate created_date = LocalDate.now() ;

    private String created_by;

    private LocalDate modified_date = LocalDate.now() ;

    private String modified_by;

    private Boolean deleted = false;
}
