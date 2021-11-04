package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchBonusAdminForm {
    private String title;
    private List<LocalDate> date;
    private Boolean status;
    private Integer pageIndex;
    private Integer pageSize;
}
