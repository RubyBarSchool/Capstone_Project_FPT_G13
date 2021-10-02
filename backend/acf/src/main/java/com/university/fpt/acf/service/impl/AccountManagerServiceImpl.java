package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountManagerServiceImpl implements AccountManagerService {
    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Override
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        accounts = accountManagerRepository.findAll();
        return accounts;
    }
}
