package com.university.fpt.acf.service;

import com.university.fpt.acf.config.security.entity.Account;

import java.util.List;

public interface AccountManagerService {
    List<Account> getAllAccounts();
}
