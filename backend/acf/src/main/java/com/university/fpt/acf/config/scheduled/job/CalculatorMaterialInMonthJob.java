package com.university.fpt.acf.config.scheduled.job;

import com.university.fpt.acf.config.scheduled.service.MaterialSuggestService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CalculatorMaterialInMonthJob extends QuartzJobBean {
    @Autowired
    private MaterialSuggestService materialSuggestService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        materialSuggestService.calculatorMaterialInMonth();
    }
}
