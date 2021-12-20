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
        //************************************
        // get list notification of user
        //************************************
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

        //************************************
        // add notification of user
        //************************************
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
    public HashMap<String, Object> deleteNotification(Notification notification) {
        //************************************
        // delete notification of user
        //************************************
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        int count = 0;
        List<Notification> notificationsOutput = new ArrayList<>();

        for (int i = notifications.size() - 1 ; i >= 0 ; i--) {
            if(notifications.get(i).equals(notification)){
                notifications.remove(i);
                continue;
            }
            notificationsOutput.add(notifications.get(i));
            if(!notifications.get(i).getRead()){
                count++;
            }
        }

        stringObjectHashMap.put("data", notificationsOutput);
        stringObjectHashMap.put("count", count);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> deleteAllNotification(String username) {
        //************************************
        // delete all notification of user
        //************************************
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        int count = 0;
        List<Notification> notificationsOutput = new ArrayList<>();

        for (int i = notifications.size() - 1 ; i >= 0 ; i--) {
            if(notifications.get(i).getUsername().equals(username)){
                notifications.remove(i);
            }
        }

        stringObjectHashMap.put("data", notificationsOutput);
        stringObjectHashMap.put("count", count);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> readAllNotification(String username) {
        //************************************
        // read all notification of user
        //************************************
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        int count = 0;
        List<Notification> notificationsOutput = new ArrayList<>();
        for (Notification notificationx : notifications) {
           if(notificationx.getUsername().equals(username)){
               notificationx.setRead(true);
               notificationsOutput.add(notificationx);
           }
        }
        stringObjectHashMap.put("data", notificationsOutput);
        stringObjectHashMap.put("count", count);
        return stringObjectHashMap;
    }

    @Override
    public HashMap<String, Object> readNotification(Notification notification) {
        //************************************
        // read notification of user
        //************************************
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        int count = 0;
        List<Notification> notificationsOutput = new ArrayList<>();
        for (Notification notificationx : notifications) {
            if(notificationx.getUsername().equals(notification.getUsername())){
                if (notificationx.equals(notification)) {
                    notificationx.setRead(true);
                }
                if(!notificationx.getRead()){
                    count++;
                }
                notificationsOutput.add(notificationx);
            }
        }
        stringObjectHashMap.put("data", notificationsOutput);
        stringObjectHashMap.put("count", count);
        return stringObjectHashMap;
    }
}
