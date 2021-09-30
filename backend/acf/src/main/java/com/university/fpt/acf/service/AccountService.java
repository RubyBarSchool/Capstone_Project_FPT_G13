package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.Account;
import com.university.fpt.acf.entity.Role;

import java.util.List;

public interface AccountService {
    Account saveAccount(Account account);
    void addRoleToAccount(String username,String roleName);
    Account getAccount(String username);
    List<Account> getAccounts();
}
