package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialSuggestFrom {
    @NotNull(message = "số lượng không được bỏ trống")
    private Integer count;
    @NotBlank(message = "loại không được bỏ trống")
    private String type;
}
