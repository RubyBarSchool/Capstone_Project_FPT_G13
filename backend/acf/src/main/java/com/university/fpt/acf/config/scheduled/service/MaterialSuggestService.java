package com.university.fpt.acf.config.scheduled.service;

import org.springframework.stereotype.Service;

@Service
public interface MaterialSuggestService {
    // The function calculates the amount of materials used by month
    void calculatorMaterialInMonth();
    // Function to calculate the amount of materials used by quarter
    void calculatorMaterialInQuarterOfYear();
    // The function calculates the amount of materials used by year
    void calculatorMaterialInYear();
}
