package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceFrom {
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "date cannot be null")
    private List<LocalDate> date;
    @NotNull(message = "type cannot be null")
    private String type;
    @NotNull(message = "note cannot be null")
    private String note;
    @NotNull(message = "pageIndex cannot be null")
    private Integer pageIndex;
    @NotNull(message = "pageSize cannot be null")
    private Integer pageSize;
    @NotNull(message = "total cannot be null")
    private Integer total;
}
