package com.university.fpt.acf.config.scheduled.service.impl;

import com.university.fpt.acf.config.scheduled.service.MaterialSuggestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MaterialSuggestServiceImpl implements MaterialSuggestService {

    @Override
    public void calculatorMaterialInMonth() {
        LocalDate localDate = LocalDate.now();
        LocalDate dateLast = localDate.minusDays(1);
        LocalDate dateStart = LocalDate.of(dateLast.getYear(), dateLast.getMonthValue(),1);
    }

    @Override
    public void calculatorMaterialInQuarterOfYear() {
        LocalDate localDate = LocalDate.now();
        LocalDate dateLast = localDate.minusDays(1);
        LocalDate monthsStart = dateLast.minusMonths(2);
        LocalDate dateStart = LocalDate.of(monthsStart.getYear(),monthsStart.getMonthValue(),1);
    }

    @Override
    public void calculatorMaterialInYear() {
        LocalDate localDate = LocalDate.now();
        LocalDate dateLast = localDate.minusDays(1);
        LocalDate dateStart = LocalDate.of(dateLast.getYear(), 1,1);
    }
}
