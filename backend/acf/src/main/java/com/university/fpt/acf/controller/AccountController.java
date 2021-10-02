package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddAccountForm;
import com.university.fpt.acf.form.GetAllAccountForm;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.form.UpdateAccountForm;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.vo.GetAllAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/spadmin/account")
public class AccountController {
    @Autowired
    private AccountManagerService accountService;

    @GetMapping
    public ResponseEntity<ResponseCommon> getAllAccount(@RequestBody GetAllAccountForm getAllAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.getAllAccounts(getAllAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PostMapping()
    public  ResponseEntity<ResponseCommon> addAccount(@RequestBody AddAccountForm addAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.insertAccount(addAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PutMapping()
    public  ResponseEntity<ResponseCommon> updateAccount(@RequestBody UpdateAccountForm updateAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.updateAccount(updateAccountForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    


}
