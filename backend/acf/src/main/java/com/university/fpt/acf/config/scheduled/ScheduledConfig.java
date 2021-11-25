package com.university.fpt.acf.config.scheduled;

import com.university.fpt.acf.config.scheduled.job.AttendanceAutoJob;
import com.university.fpt.acf.config.scheduled.job.AttendanceCheckJob;
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
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 10 * * ?"))
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
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 10 * * ?"))
                .build();
    }

}
