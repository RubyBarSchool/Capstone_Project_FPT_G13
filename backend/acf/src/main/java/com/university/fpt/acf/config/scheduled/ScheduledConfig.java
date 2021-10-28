package com.university.fpt.acf.config.scheduled;

import com.university.fpt.acf.config.scheduled.job.AttendanceCheckJob;
//import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScheduledConfig {
//    @Bean
//    public JobDetail printRandomJobDetail() {
//        return JobBuilder
//                .newJob(AttendanceCheckJob.class)
//                .withIdentity(JobKey.jobKey("checkAttendance"))
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger printRandomJobTrigger() {
//        return TriggerBuilder
//                .newTrigger()
//                .forJob(printRandomJobDetail())
//                .withIdentity(TriggerKey.triggerKey("checkAttendance"))
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ?"))
//                .build();
//    }
}
