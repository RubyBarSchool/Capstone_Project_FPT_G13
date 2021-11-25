package com.university.fpt.acf.config.scheduled.job;

import com.university.fpt.acf.config.scheduled.service.SalaryJobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SalaryAutoJob extends QuartzJobBean {
    @Autowired
    private SalaryJobService salaryJobService;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        salaryJobService.payroll();
    }
}
