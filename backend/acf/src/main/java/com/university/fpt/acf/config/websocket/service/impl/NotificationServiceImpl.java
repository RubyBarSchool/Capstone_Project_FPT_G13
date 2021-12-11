package com.university.fpt.acf.config.websocket.service.impl;

import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    public static List<Notification> notifications = new ArrayList<>();

    @Override
    public HashMap<String, Object> getListNotification(String username) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        int count = 0;
        List<Notification> notificationsOutput = new ArrayList<>();
        for (Notification notification : notifications) {
            if (notification.getUsername().equals(username)) {
                notificationsOutput.add(notification);
                if (!notification.getRead()) {
                    count++;
                }
            }
        }
        stringObjectHashMap.put("data", notificationsOutput);
        stringObjectHashMap.put("count", count);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> addNotification(Notification notification) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        int count = 0;
        List<Notification> notificationsOutput = new ArrayList<>();
        for (Notification notificationx : notifications) {
            if (notificationx.getUsername().equals(notification.getUsername())) {
                notificationsOutput.add(notificationx);
                if (!notificationx.getRead()) {
                    count++;
                }
            }
        }
        count++;
        notificationsOutput.add(0,notification);
        notifications.add(0, notification);
        stringObjectHashMap.put("data", notificationsOutput);
        stringObjectHashMap.put("count", count);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> deleteNotification(String username, Notification notification) {
        return null;
    }

    @Override
    public void deleteAllNotification(String username) {

    }

    @Override
    public void readAllNotification(String username) {

    }

    @Override
    public HashMap<String, Object> readNotification(String username, Notification notification) {
        return null;
    }
}
