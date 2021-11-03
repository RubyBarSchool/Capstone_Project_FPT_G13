package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchSalaryForm {
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "position cannot be null")
    private List<Long> idPositons;
    @NotNull(message = "date cannot be null")
    private List<LocalDate> date;
    @NotNull(message = "pageIndex cannot be null")
    private Integer pageIndex;
    @NotNull(message = "pageSize cannot be null")
    private Integer pageSize;
    @NotNull(message = "total cannot be null")
    private Integer total;
}
