package com.university.fpt.acf.config.scheduled.service;

import org.springframework.stereotype.Service;

@Service
public interface MaterialSuggestService {
    void calculatorMaterialInMonth();
    void calculatorMaterialInQuarterOfYear();
    void calculatorMaterialInYear();
}
