package com.university.fpt.acf.config.scheduled.job;

import com.university.fpt.acf.config.scheduled.service.AttendanceCheckService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AttendanceCheckJob extends QuartzJobBean  {
    @Autowired
    private AttendanceCheckService attendanceCheckService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //************************************
        // Configure Quatz's execution function to check which employees have not been timekeeping
        //************************************
        attendanceCheckService.checkAttendance();
    }

}
