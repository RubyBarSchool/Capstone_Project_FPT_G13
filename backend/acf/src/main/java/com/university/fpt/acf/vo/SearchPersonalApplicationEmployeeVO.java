package com.university.fpt.acf.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchPersonalApplicationEmployeeVO {
    private Long idApplication;
    private String fileAttach;
    private String title;
    private String comment;
    private String content;
    private Boolean statusAccept;
}
