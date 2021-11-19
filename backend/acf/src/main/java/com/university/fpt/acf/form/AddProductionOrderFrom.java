package com.university.fpt.acf.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductionOrderFrom {
    private Long id;
    private String name;
    private Long idContact;
    private List<Long> idEmployees;
    private Long idProduct;
    private LocalDate dateStart;
    private LocalDate dateEnd;
}
