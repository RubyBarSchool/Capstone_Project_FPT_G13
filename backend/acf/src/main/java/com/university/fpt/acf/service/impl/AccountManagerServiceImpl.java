package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountManagerServiceImpl implements AccountManagerService {
    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        AccountSercurity accountSercurity = new AccountSercurity();
        String username = accountSercurity.getUserName();
        accounts = accountManagerRepository.findAll();
        return accounts;
    }
}
