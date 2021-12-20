package com.university.fpt.acf.config.security.service;

import com.university.fpt.acf.config.security.entity.Account;

import java.util.List;

public interface AccountService {
    // Add account
    Account saveAccount(Account account);
    // Add a new role for account
    void addRoleToAccount(String username,String roleName);
    // Get account by username
    Account getAccount(String username);
    //Get all accounts in system
    List<Account> getAccounts();
}
