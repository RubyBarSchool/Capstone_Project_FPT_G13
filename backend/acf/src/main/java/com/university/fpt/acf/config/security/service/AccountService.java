package com.university.fpt.acf.config.security.service;

import com.university.fpt.acf.config.security.entity.Account;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);
    void addRoleToAccount(String username,String roleName);
    Account getAccount(String username);
    List<Account> getAccounts();
}
