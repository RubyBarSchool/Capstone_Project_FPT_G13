package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchPersonalLeaveApplicationAdminVO {
    private Long idApplication;
    private LocalDate date;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String fileAttach;
    private String title;
    private String comment;
    private String content;
    private Long idEmployee;
    private String nameEmployee;
    private String statusAccept;
    private  LocalDate dateAccept;
}
