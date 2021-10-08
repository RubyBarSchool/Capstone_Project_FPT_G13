package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AccountManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/spadmin/account")
public class AccountController {
    @Autowired
    private AccountManagerService accountService;

    @PostMapping(path = "/allaccount")
    public ResponseEntity<ResponseCommon> getAllAccount(@RequestBody GetAllAccountForm getAllAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.getAllAccounts(getAllAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PostMapping("/getAcc")
    public ResponseEntity<ResponseCommon> GetAccountByID(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.getAccountById(id));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }

    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addAccount(@RequestBody AddAccountForm addAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.insertAccount(addAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updateAccount(@RequestBody UpdateAccountForm updateAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.updateAccount(updateAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deleteAccount(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.deleteAccount(id));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PostMapping(path = "/searchaccount")
    public ResponseEntity<ResponseCommon> searchAccount(@RequestBody SearchAccountForm searchAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.searchAccount(searchAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PostMapping("/generateUsername")
    public ResponseEntity<ResponseCommon> generateUsername(@RequestParam String fullname){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.GenerateUsername(fullname));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }





}
