package com.university.fpt.acf.config.scheduled.job;

import com.university.fpt.acf.config.scheduled.service.AttendanceCheckService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


public class AttendanceAutoJob  extends QuartzJobBean{

    @Autowired
    private AttendanceCheckService attendanceCheckService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //************************************
        // Configure Quatz's execution function to automatically take attendance for employees
        //************************************
        attendanceCheckService.autoAttendance();
    }

}
