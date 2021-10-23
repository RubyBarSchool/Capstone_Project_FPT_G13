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
public class AddAttendanceForm {
    @NotNull(message = "date cannot be null")
    private LocalDate date;
    @NotBlank(message = "type cannot be blank")
    private String type;
    @NotNull(message = "attendance cannot be null")
    private List<AttendanceNote> attendance;
}
