package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAttendanceForm {

    @NotNull(message = "id cannot be null")
    private Long id;

    @NotBlank(message = "type cannot blank")
    private String type;

    @NotNull(message = "note cannot be null")
    private String note;

}
