package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.ChangePasswordAccountForm;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.DetailEmployeeVO;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/employee")
public class ProfileController {

    @Autowired
    private EmployeeService employeeService;


    @Autowired
    private AccountManagerService accountService;

    //************************************
    // Get detail employee of information by username employee
    //************************************
    @GetMapping("/getEmployeeUsername")
    public ResponseEntity<ResponseCommon> GetEmployeeDetailByUsername(@RequestParam String username){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        EmployeeDetailVO em = new EmployeeDetailVO();
        try{
            em = employeeService.getEmployeeDetailByUsername(username);
            if(em==null){
                message ="Không tồn tại";
            }
            message ="Thành công";
            responseCommon.setMessage(message);
            responseCommon.setData(em);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);

        }catch (Exception e){
            message=e.getMessage();
            responseCommon.setMessage(message);
            responseCommon.setData(em);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    //************************************
    // Get profile
    //************************************
    @GetMapping("/profile")
    public ResponseEntity<ResponseCommon> getProfile(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        DetailEmployeeVO em = new DetailEmployeeVO();
        try{
            em = employeeService.getDetailEmployeeByUsername();
            if(em==null){
                message ="Không tồn tại";
            }
            message ="Thành công";
            responseCommon.setMessage(message);
            responseCommon.setData(em);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);

        }catch (Exception e){
            message=e.getMessage();
            responseCommon.setMessage(message);
            responseCommon.setData(em);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }

    //************************************
    // Get profile
    //************************************
    @GetMapping("/changeimage")
    public ResponseEntity<ResponseCommon> changeImage(@RequestParam String image){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean em = false;
        try{
            em = employeeService.changeImage(image);
            if(em==false){
                message ="Sửa ảnh không thành công";
            }
            message ="Sửa ảnh thành công";
            responseCommon.setMessage(message);
            responseCommon.setData(em);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);

        }catch (Exception e){
            message=e.getMessage();
            responseCommon.setMessage(message);
            responseCommon.setData(em);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }

    //************************************
    // Change password for user
    //************************************
    @PostMapping("/changepassword")
    public ResponseEntity<ResponseCommon> changePassword(@RequestBody ChangePasswordAccountForm changePasswordAccountForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean checkUpdate = false;
        try {
            checkUpdate = accountService.changePassword(changePasswordAccountForm);
            if (checkUpdate) {
                message = "Thay đổi mật khẩu thành công!";
            } else {
                message = "Mật khẩu cũ không đúng!";
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
}
