package com.university.fpt.acf.config.websocket.service.impl;

import com.university.fpt.acf.config.websocket.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    public static List<String> userOnline = new ArrayList<>();

    @Override
    public List<String> getListUserOnline() {
        return userOnline;
    }

    @Override
    public void deleteUserLogout(String username) {
        for(int i = 0 ; i < userOnline.size() ; i++){
            if(userOnline.get(i).equals(username)){
                userOnline.remove(i);
                break;
            }
        }
    }

    @Override
    public void insertUserLogout(String username) {
        userOnline.add(username);
    }
}
