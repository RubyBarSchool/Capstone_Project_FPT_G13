package com.university.fpt.acf.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.username}")
    private String SMTP_AUTH_USER;
    @Value("${spring.mail.password}")
    private String SMTP_AUTH_PWD;
    @Value("${spring.mail.host}")
    private String SMTP_HOST_NAME;
    @Value("${spring.mail.port}")
    private int SMTP_HOST_PORT;


    @Bean
    public JavaMailSender getJavaMailSender() {
        //************************************
        // Configure the javamailsender object with the following information:
        // 1. hostname
        // 2. port
        // 3. username
        // 4. password
        // 5. properties
        //************************************
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(SMTP_HOST_NAME);
        mailSender.setPort(SMTP_HOST_PORT);

        mailSender.setUsername(SMTP_AUTH_USER);
        mailSender.setPassword(SMTP_AUTH_PWD);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
