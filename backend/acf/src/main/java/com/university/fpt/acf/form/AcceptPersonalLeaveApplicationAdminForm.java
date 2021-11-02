package com.university.fpt.acf.form;

import lombok.Data;

@Data
public class AcceptPersonalLeaveApplicationAdminForm {
    private Long idApplication;
    private Long idAcceptEmployee;
    private String comment;
}
