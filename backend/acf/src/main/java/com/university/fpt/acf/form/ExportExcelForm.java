package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportExcelForm {
    @NotNull
    private  AttendanceFrom dataSearch;
    @NotBlank
    private String type;
    @NotBlank
    private String note;
}
