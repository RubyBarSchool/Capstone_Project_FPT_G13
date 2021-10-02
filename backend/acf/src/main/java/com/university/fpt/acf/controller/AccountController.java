package com.university.fpt.acf.controller;

import com.university.fpt.acf.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/spadmin/account")
public class AccountController {
    @Autowired
    private AccountManagerService accountService;

    @GetMapping()
    public ResponseEntity<?> authenticateUser() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
