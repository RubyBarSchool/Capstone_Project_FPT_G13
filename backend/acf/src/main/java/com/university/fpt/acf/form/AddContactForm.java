package com.university.fpt.acf.form;

import com.university.fpt.acf.vo.FileContactVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddContactForm {
    @NotBlank(message = "Tên hợp đồng không được để trống")
    private String name;

    @NotNull(message = "Hạn hợp đồng bắt buộc phải chọn")
    private LocalDate time;

    @NotNull(message = "Công ty đối tác bắt buộc phải chọn")
    private Long idCompany;

    private FileContactVO fileExcel;
}
