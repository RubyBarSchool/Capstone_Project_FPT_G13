package com.university.fpt.acf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/mail")
public class MailController {

    @Autowired
    public JavaMailSender emailSender;


    @GetMapping
    public String sendSimpleEmail(@RequestParam String mail) {

        // Create a Simple MailMessage.
        try{
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(mail);
            message.setSubject("Test Simple Email");
            message.setText("Hello, Im testing Simple Email");
            // Send Message!
            this.emailSender.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Email Sent!";
    }

}
