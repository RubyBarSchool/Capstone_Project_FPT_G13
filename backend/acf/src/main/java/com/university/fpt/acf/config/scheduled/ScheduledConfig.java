package com.university.fpt.acf.config.scheduled;

import com.university.fpt.acf.config.scheduled.job.*;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ScheduledConfig {

    @Bean
    public JobDetail jobCheckAttendance() {
        //************************************
        // Create a jobdetail with information and running content is attendance check
        //************************************
        return JobBuilder
                .newJob(AttendanceCheckJob.class)
                .withIdentity(JobKey.jobKey("checkAttendance"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCheckAttendance() {
        //************************************
        // Create a trigger associated with the job, the attendance check will automatically run every 15 minutes from 6pm to 7pm daily
        //************************************
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCheckAttendance())
                .withIdentity(TriggerKey.triggerKey("checkAttendance"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 18 * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobAttendanceAuto() {
        //************************************
        // Create a jobdetail with information and running content is attendance for employees
        //************************************
        return JobBuilder
                .newJob(AttendanceAutoJob.class)
                .withIdentity(JobKey.jobKey("attendaneAuto"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerAttendanceAuto() {
        //************************************
        // Create a trigger attached to the job that automatically takes attendance for employees at 11pm every day
        //************************************
        return TriggerBuilder
                .newTrigger()
                .forJob(jobAttendanceAuto())
                .withIdentity(TriggerKey.triggerKey("attendaneAuto"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 23 * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobCalculatorMaterialInMonth() {
        //************************************
        // Create a jobdetail with information and running content is calculating the amount of material used in the last month
        //************************************
        return JobBuilder
                .newJob(CalculatorMaterialInMonthJob.class)
                .withIdentity(JobKey.jobKey("calculatorMaterialInMonth"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCalculatorMaterialInMonth() {
        //************************************
        // Create a trigger attached to a job calculates material usage in the previous month on the 1st of every month
        //************************************
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCalculatorMaterialInMonth())
                .withIdentity(TriggerKey.triggerKey("calculatorMaterialInMonth"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 1 * ?"))
                .build();
    }

    @Bean
    public JobDetail jobCalculatorMaterialnQuarterOfYear() {
        //************************************
        // Create a jobdetail with information and running content is calculating the amount of material used in the last quarter
        //************************************
        return JobBuilder
                .newJob(CalculatorMaterialnQuarterOfYearJob.class)
                .withIdentity(JobKey.jobKey("calculatorMaterialnQuarterOfYear"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCalculatorMaterialnQuarterOfYear() {
        //************************************
        // tạo ra một trigger associated with a job calculates material usage in the previous quarter on the 1st of every month 1, 4, 7, and 10
        //************************************
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCalculatorMaterialnQuarterOfYear())
                .withIdentity(TriggerKey.triggerKey("calculatorMaterialnQuarterOfYear"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 1 1,4,7,10 ?"))
                .build();
    }

    @Bean
    public JobDetail jobCalculatorMaterialInYear() {
        //************************************
        // Create a jobdetail with information and running content is calculating the amount of material used in the last year
        //************************************
        return JobBuilder
                .newJob(CalculatorMaterialnQuarterOfYearJob.class)
                .withIdentity(JobKey.jobKey("calculatorMaterialInYear"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCalculatorMaterialInYear() {
        //************************************
        // Create a trigger associated with a job calculates material usage in the previous year on the 1st of every month
        //************************************
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCalculatorMaterialInYear())
                .withIdentity(TriggerKey.triggerKey("calculatorMaterialInYear"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 1 1 ? *"))
                .build();
    }

    @Bean
    public JobDetail jobSalary() {
        //************************************
        // Create a jobdetail with information and running content is automatically generating new payroll
        //************************************
        return JobBuilder
                .newJob(SalaryAutoJob.class)
                .withIdentity(JobKey.jobKey("salary"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerSalary() {
        //************************************
        // Create a trigger gắn với job tạo ra bảng lương mới vào 1 h ngày 10 hàng tháng
        //************************************
        return TriggerBuilder
                .newTrigger()
                .forJob(jobSalary())
                .withIdentity(TriggerKey.triggerKey("salary"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 10 * ?"))
                .build();
    }

}
