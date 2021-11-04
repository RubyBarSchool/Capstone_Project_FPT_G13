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
public class BonusPunishForm {
    private LocalDate date;
    @NotNull(message = "CheckNow cannot be null")
    private Boolean checkNow;
    @NotNull(message = "pageIndex cannot be null")
    private Integer pageIndex;
    @NotNull(message = "pageSize cannot be null")
    private Integer pageSize;
    @NotNull(message = "total cannot be null")
    private Integer total;
}
