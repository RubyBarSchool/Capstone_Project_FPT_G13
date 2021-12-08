package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNotAttendanceForm {
    @NotNull(message = "date cannot be null")
    private LocalDate date;
    @NotNull(message = "pageIndex cannot be null")
    private Integer pageIndex;
    @NotNull(message = "pageSize cannot be null")
    private Integer pageSize;
}
