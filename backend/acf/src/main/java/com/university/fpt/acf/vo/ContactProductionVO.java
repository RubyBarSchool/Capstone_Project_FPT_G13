package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactProductionVO {
    private Long idContact;
    private String name;
    private LocalDate dateCreate;
    private LocalDate dateFinish;
}
