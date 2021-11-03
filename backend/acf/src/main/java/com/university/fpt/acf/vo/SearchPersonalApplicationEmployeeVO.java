package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPersonalApplicationEmployeeVO {
    private Long idApplication;
    private LocalDate date;
    private String fileAttach;
    private String title;
    private String comment;
    private String content;
    private String statusAccept;
}
