package com.university.fpt.acf.config.websocket.service;

import com.university.fpt.acf.config.websocket.model.Notification;

import java.util.HashMap;
import java.util.List;

public interface NotificationService {
    HashMap<String, Object> getListNotification(String username);
    HashMap<String, Object> addNotification(Notification notification);
    HashMap<String, Object> deleteNotification(String username,Notification notification);
    void deleteAllNotification(String username);
    void readAllNotification(String username);
    HashMap<String, Object> readNotification(String username,Notification notification);
}
