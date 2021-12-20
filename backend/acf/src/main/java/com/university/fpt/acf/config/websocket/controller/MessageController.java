package com.university.fpt.acf.config.websocket.controller;

import com.university.fpt.acf.config.websocket.model.DataReponseWebSocket;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.config.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/hello")
    public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
        //************************************
        // get message login
        //************************************
        String message = "Hello from " + sha.getUser().getName();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("mess", message);
        DataReponseWebSocket dataReponseWebSocket = new DataReponseWebSocket(stringStringHashMap);
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", dataReponseWebSocket);
    }

    @MessageMapping("/login")
    public void addUserOnline(SimpMessageHeaderAccessor sha) {
        //************************************
        // add user after login and return all user login 
        //************************************
        this.userService.insertUserLogout(sha.getUser().getName());
        List<String> userLogin = this.userService.getListUserOnline();
        for (String s : userLogin) {
            simpMessagingTemplate.convertAndSendToUser(s, "/queue/online", userLogin);
        }
    }

    @MessageMapping("/online")
    public void getUserOnline(SimpMessageHeaderAccessor sha) {
        //************************************
        //get user is online
        //************************************
        List<String> userLogin = this.userService.getListUserOnline();
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/online", userLogin);
    }

    @MessageMapping("/logout")
    public void deleteUserOnline(SimpMessageHeaderAccessor sha) {
        //************************************
        // delete user in list user online
        //************************************
        this.userService.deleteUserLogout(sha.getUser().getName());
        List<String> userLogin = this.userService.getListUserOnline();
        for (String s : userLogin) {
            simpMessagingTemplate.convertAndSendToUser(s, "/queue/online", userLogin);
        }
    }

    @MessageMapping("/notification")
    public void getNotification(SimpMessageHeaderAccessor sha) {
        //************************************
        // get notification of user login
        //************************************
        HashMap<String, Object> notifications = this.notificationService.getListNotification(sha.getUser().getName());
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/notification", notifications);
    }

    @MessageMapping("/readnotification")
    public void readNotification(SimpMessageHeaderAccessor sha, @Payload Notification notification) {
        //************************************
        // read notification of user login
        //************************************
        HashMap<String, Object> notifications = this.notificationService.readNotification(notification);
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/notification", notifications);
    }

    @MessageMapping("/readallnotification")
    public void readAllNotification(SimpMessageHeaderAccessor sha) {
        //************************************
        // read all notification of user login
        //************************************
        HashMap<String, Object> notifications = this.notificationService.readAllNotification(sha.getUser().getName());
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/notification", notifications);
    }

    @MessageMapping("/deleteallnotification")
    public void deleteAllNotification(SimpMessageHeaderAccessor sha) {
        //************************************
        // delete all notification of user login
        //************************************
        HashMap<String, Object> notifications = this.notificationService.deleteAllNotification(sha.getUser().getName());
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/notification", notifications);
    }

    @MessageMapping("/deletenotification")
    public void deleteNotification(SimpMessageHeaderAccessor sha, @Payload Notification notification) {
        //************************************
        // delete notification of user login
        //************************************
        HashMap<String, Object> notifications = this.notificationService.deleteNotification(notification);
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/notification", notifications);
    }

}
