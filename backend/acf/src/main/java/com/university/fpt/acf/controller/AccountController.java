package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.vo.GetAccountDetailResponeVO;
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
        Integer total = 0;
        try {
            getAllAccountResponseVOS = accountService.searchAccount(searchAccountForm);
            total = accountService.getTotalSearchAccount(searchAccountForm);
            responseCommon.setData(getAllAccountResponseVOS);
            responseCommon.setTotal(total);
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


    @PostMapping("/getAcc")
    public ResponseEntity<ResponseCommon> GetAccountByID(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        GetAccountDetailResponeVO getAccountDetailResponeVO = new GetAccountDetailResponeVO();
        try {
            getAccountDetailResponeVO = accountService.getAccountById(id);
            if(getAccountDetailResponeVO==null){
                message="Account not exist";
            }else {
                message="Get Account sucessfuly";

            }
            responseCommon.setMessage(message);
            responseCommon.setData(getAccountDetailResponeVO);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = "Could not get accounts !";
            responseCommon.setData(getAccountDetailResponeVO);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addAccount(@RequestBody AddAccountForm addAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd =accountService.insertAccount(addAccountForm);
            if(checkAdd==false){
                message="Add account fail!";
            }else{
                message="Add sucessfuly!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = "Can't insert account!";
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updateAccount(@RequestBody UpdateAccountForm updateAccountForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            checkUpdate =accountService.updateAccount(updateAccountForm);
            if(checkUpdate==false){
                message="Update account fail!";
            }else{
                message="Update sucessfuly!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(accountService.updateAccount(updateAccountForm));
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = "Can't update account!";
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deleteAccount(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = accountService.deleteAccount(id);
            if(checkDelete==false){
                message="Delete account fail!";
            }else{
                message="Delete account sucessfuly";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = "Can't delete account!";
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @GetMapping("/generateUsername")
    public ResponseEntity<ResponseCommon> generateUsername(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message ="";
        String genUsername="";
        try {
            genUsername = accountService.GenerateUsername(id);
            if(genUsername.isEmpty()){
                message ="Dont GenerateUsername";
            }else {
                message="GenerateUsername sucessfuly";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(accountService.GenerateUsername(id));
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = "Can't generateUsername!";
            responseCommon.setData(genUsername);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }





}
