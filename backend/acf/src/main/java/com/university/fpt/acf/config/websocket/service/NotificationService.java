package com.university.fpt.acf.config.websocket.service;

import com.university.fpt.acf.config.websocket.model.Notification;

import java.util.HashMap;
import java.util.List;

public interface NotificationService {
    HashMap<String, Object> getListNotification(String username);
    HashMap<String, Object> addNotification(Notification notification);
    HashMap<String, Object> deleteNotification(Notification notification);
    HashMap<String, Object> deleteAllNotification(String username);
    HashMap<String, Object> readAllNotification(String username);
    HashMap<String, Object> readNotification(Notification notification);
}
