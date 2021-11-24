package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateContractForm {
    private Long id;

    @NotBlank(message = "Tên hợp đồng không được để trống")
    private String name;

    @NotNull(message = "Hạn hợp đồng bắt buộc phải chọn")
    private LocalDate dateFinish;
}
