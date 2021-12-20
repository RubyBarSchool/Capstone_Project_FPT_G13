package com.university.fpt.acf.config.websocket.service;

import com.university.fpt.acf.config.websocket.model.Notification;

import java.util.HashMap;
import java.util.List;

public interface NotificationService {
    // get list notification of user
    HashMap<String, Object> getListNotification(String username);
    // add notification of user
    HashMap<String, Object> addNotification(Notification notification);
    // delete notification of user
    HashMap<String, Object> deleteNotification(Notification notification);
    // delete all notification of user
    HashMap<String, Object> deleteAllNotification(String username);
    // read notification of user
    HashMap<String, Object> readAllNotification(String username);
    // read all notification of user
    HashMap<String, Object> readNotification(Notification notification);
}
