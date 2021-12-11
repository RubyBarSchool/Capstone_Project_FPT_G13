package com.university.fpt.acf.config.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value( "${spring.mail.username}" )
    private String mail;
    @Value( "${spring.mail.password}" )
    private String password;
    @Value( "${spring.mail.host}" )
    private String hostName;
    @Value( "${spring.mail.port}" )
    private int portCode;

    @Value( "${acf.email.host}" )
    private String SMTP_HOST_NAME;

    @Value( "${acf.email.username}" )
    private String SMTP_AUTH_USER;

    @Value( "${acf.email.password}" )
    private String SMTP_AUTH_PWD;

    @Value( "${acf.email.port}" )
    private int SMTP_HOST_PORT;



    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(hostName);
        mailSender.setPort(portCode);

        mailSender.setUsername(mail);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
