package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.vo.GetAllAccountResponseVO;
import com.university.fpt.acf.vo.GetAllRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/spadmin/account")
public class AccountController {
    @Autowired
    private AccountManagerService accountService;

    @PostMapping(path = "/search")
    public ResponseEntity<ResponseCommon> searchAccount(@RequestBody SearchAccountForm searchAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<GetAllAccountResponseVO> getAllAccountResponseVOS = new ArrayList<>();
        try {
            getAllAccountResponseVOS = accountService.searchAccount(searchAccountForm);
            responseCommon.setData(getAllAccountResponseVOS);
            message = "Get accounts successfully";
            if(getAllAccountResponseVOS.isEmpty()){
                message = "Get accounts not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get accounts !";
            responseCommon.setData(getAllAccountResponseVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

//    @PostMapping(path = "/allaccount")
//    public ResponseEntity<ResponseCommon> getAllAccount(@RequestBody GetAllAccountForm getAllAccountForm){
//        ResponseCommon responseCommon = new ResponseCommon();
//        responseCommon.setData(accountService.getAllAccounts(getAllAccountForm));
//        responseCommon.setStatus(HttpStatus.OK.value());
//        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
//    }
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

    @GetMapping("/generateUsername")
    public ResponseEntity<ResponseCommon> generateUsername(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(accountService.GenerateUsername(id));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }





}
