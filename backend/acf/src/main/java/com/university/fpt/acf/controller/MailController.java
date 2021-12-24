package com.university.fpt.acf.controller;

import com.university.fpt.acf.config.scheduled.service.AttendanceCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/mail")
public class MailController {

    @Autowired
    private AttendanceCheckService attendanceCheckService;
    //************************************
    // Create a Simple MailMessage.
    //************************************

    @GetMapping
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        try{
            attendanceCheckService.checkAttendance();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "Email Sent";
    }

}
