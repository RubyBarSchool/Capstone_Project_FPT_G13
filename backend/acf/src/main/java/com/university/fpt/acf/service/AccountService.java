package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.Account;
import com.university.fpt.acf.entity.Role;

import java.util.List;

public interface AccountService {
    Account saveUser(Account account);
    void addRoleToUser(String username,String roleName);
    Account getUser(String username);
    List<Account> getUsers();
}
