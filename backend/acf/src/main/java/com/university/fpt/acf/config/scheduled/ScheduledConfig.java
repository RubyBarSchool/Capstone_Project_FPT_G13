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
        return JobBuilder
                .newJob(AttendanceCheckJob.class)
                .withIdentity(JobKey.jobKey("checkAttendance"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCheckAttendance() {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCheckAttendance())
                .withIdentity(TriggerKey.triggerKey("checkAttendance"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/15 18 * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobAttendanceAuto() {
        return JobBuilder
                .newJob(AttendanceAutoJob.class)
                .withIdentity(JobKey.jobKey("attendaneAuto"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerAttendanceAuto() {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobAttendanceAuto())
                .withIdentity(TriggerKey.triggerKey("attendaneAuto"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 23 * * ?"))
                .build();
    }

    @Bean
    public JobDetail jobCalculatorMaterialInMonth() {
        return JobBuilder
                .newJob(CalculatorMaterialInMonthJob.class)
                .withIdentity(JobKey.jobKey("calculatorMaterialInMonth"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCalculatorMaterialInMonth() {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCalculatorMaterialInMonth())
                .withIdentity(TriggerKey.triggerKey("calculatorMaterialInMonth"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 1 * ?"))
                .build();
    }

    @Bean
    public JobDetail jobCalculatorMaterialnQuarterOfYear() {
        return JobBuilder
                .newJob(CalculatorMaterialnQuarterOfYearJob.class)
                .withIdentity(JobKey.jobKey("calculatorMaterialnQuarterOfYear"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCalculatorMaterialnQuarterOfYear() {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCalculatorMaterialnQuarterOfYear())
                .withIdentity(TriggerKey.triggerKey("calculatorMaterialnQuarterOfYear"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 1 1,4,7,10 ?"))
                .build();
    }

    @Bean
    public JobDetail jobCalculatorMaterialInYear() {
        return JobBuilder
                .newJob(CalculatorMaterialnQuarterOfYearJob.class)
                .withIdentity(JobKey.jobKey("calculatorMaterialInYear"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerCalculatorMaterialInYear() {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobCalculatorMaterialInYear())
                .withIdentity(TriggerKey.triggerKey("calculatorMaterialInYear"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 1 1 ? *"))
                .build();
    }

    @Bean
    public JobDetail jobSalary() {
        return JobBuilder
                .newJob(SalaryAutoJob.class)
                .withIdentity(JobKey.jobKey("salary"))
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger triggerSalary() {
        return TriggerBuilder
                .newTrigger()
                .forJob(jobSalary())
                .withIdentity(TriggerKey.triggerKey("salary"))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 10 * ?"))
                .build();
    }

}
