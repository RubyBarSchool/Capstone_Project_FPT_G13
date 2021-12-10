package com.university.fpt.acf.config.websocket.controller;

import com.university.fpt.acf.config.websocket.model.DataReponseWebSocket;
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

    @MessageMapping("/hello")
    public void send(SimpMessageHeaderAccessor sha, @Payload String username) {
        String message = "Hello from " + sha.getUser().getName();
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("mess", message);
        DataReponseWebSocket dataReponseWebSocket = new DataReponseWebSocket(stringStringHashMap);
        simpMessagingTemplate.convertAndSendToUser(username, "/queue/messages", dataReponseWebSocket);
    }

    @MessageMapping("/login")
    public void addUserOnline(SimpMessageHeaderAccessor sha) {
        this.userService.insertUserLogout(sha.getUser().getName());
        List<String> userLogin = this.userService.getListUserOnline();
        for (String s : userLogin) {
            simpMessagingTemplate.convertAndSendToUser(s, "/queue/online", userLogin);
        }
    }

    @MessageMapping("/online")
    public void getUserOnline(SimpMessageHeaderAccessor sha) {
        List<String> userLogin = this.userService.getListUserOnline();
        simpMessagingTemplate.convertAndSendToUser(sha.getUser().getName(), "/queue/online", userLogin);
    }

    @MessageMapping("/logout")
    public void deleteUserOnline(SimpMessageHeaderAccessor sha) {
        this.userService.deleteUserLogout(sha.getUser().getName());
        List<String> userLogin = this.userService.getListUserOnline();
        for (String s : userLogin) {
            simpMessagingTemplate.convertAndSendToUser(s, "/queue/online", userLogin);
        }
    }
}
