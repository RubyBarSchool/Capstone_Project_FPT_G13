package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeNotAttendanceForm {
    private LocalDate date;
    private Integer pageIndex;
    private Integer pageSize;
}
