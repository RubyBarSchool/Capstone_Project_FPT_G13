package com.university.fpt.acf.config.websocket.service;

import java.util.List;

public interface UserService {
    List<String> getListUserOnline();
    void deleteUserLogout(String username);
    void insertUserLogout(String username);
}
