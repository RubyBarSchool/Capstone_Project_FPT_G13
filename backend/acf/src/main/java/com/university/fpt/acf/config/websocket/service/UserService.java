package com.university.fpt.acf.config.websocket.service;

import java.util.List;

public interface UserService {
    // get list user online
    List<String> getListUserOnline();
    // delete user logout
    void deleteUserLogout(String username);
    // add user login
    void insertUserLogout(String username);
}
