package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.util.AccountValidate.AddAccountValidate;
import com.university.fpt.acf.vo.GetAccountDetailResponeVO;
import com.university.fpt.acf.vo.GetAllAccountResponseVO;
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
    public ResponseEntity<ResponseCommon> searchAccount(@RequestBody SearchAccountForm searchAccountForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<GetAllAccountResponseVO> getAllAccountResponseVOS = new ArrayList<>();
        Integer total = 0;
        try {
            getAllAccountResponseVOS = accountService.searchAccount(searchAccountForm);
            total = accountService.getTotalSearchAccount(searchAccountForm);
            responseCommon.setData(getAllAccountResponseVOS);
            responseCommon.setTotal(total);
            message = "Thành công";
            if (total.intValue() == 0) {
                message = "Không tìm thấy";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllAccountResponseVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }


    @PostMapping("/getAcc")
    public ResponseEntity<ResponseCommon> GetAccountByID(@RequestParam Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        GetAccountDetailResponeVO getAccountDetailResponeVO = new GetAccountDetailResponeVO();
        try {
            getAccountDetailResponeVO = accountService.getAccountById(id);
            if (getAccountDetailResponeVO == null) {
                message = "Tài khoản không tồn tại";
            } else {
                message = "Thành công";

            }
            responseCommon.setMessage(message);
            responseCommon.setData(getAccountDetailResponeVO);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAccountDetailResponeVO);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addAccount(@RequestBody AddAccountForm addAccountForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean checkAdd = false;
        try {
            checkAdd = checkAdd = accountService.insertAccount(addAccountForm);
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseCommon> updateAccount(@RequestBody UpdateAccountForm updateAccountForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean checkUpdate = false;
        try {
            checkUpdate = accountService.updateAccount(updateAccountForm);
            if (checkUpdate == false) {
                message = "Chỉnh sửa không thành công!";
            } else {
                message = "Chỉnh sửa thành công!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteAccount(@RequestParam Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean checkDelete = false;
        try {
            checkDelete = accountService.deleteAccount(id);
            if (checkDelete == false) {
                message = "Xóa không thành công";
            } else {
                message = "Xóa thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @GetMapping("/generateUsername")
    public ResponseEntity<ResponseCommon> generateUsername(@RequestParam Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        String genUsername = "";
        try {
            genUsername = accountService.GenerateUsername(id);
            message = "Generate tên tài khoản thành công";
            if (genUsername == null || genUsername.isEmpty()) {
                message = "Generate tên tài khoản không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(accountService.GenerateUsername(id));
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(genUsername);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }


}
