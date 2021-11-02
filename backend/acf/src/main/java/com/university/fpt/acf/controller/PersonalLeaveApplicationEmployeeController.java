package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AcceptPersonalLeaveApplicationAdminForm;
import com.university.fpt.acf.form.AddPerLeaveAppEmployeeForm;
import com.university.fpt.acf.form.UpdatePersonalAppEmployeeForm;
import com.university.fpt.acf.service.PersonalLeaveApplicationAdminService;
import com.university.fpt.acf.service.PersonalLeaveApplicationEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/personalLeaveApplicationEmployee")
public class PersonalLeaveApplicationEmployeeController {
    @Autowired
    PersonalLeaveApplicationEmployeeService personalLeaveApplicationService;
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addPersonalApplication(@RequestBody AddPerLeaveAppEmployeeForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {

                if(addForm==null){
                    message="Đơn gửi đang trống!";
                }
                checkAdd = personalLeaveApplicationService.AddLeaveApplication(addForm);
                if(checkAdd==true){
                    message="Thêm đơn thành công!";
                }else{
                    message="Thêm đơn không thành công!";
                }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseCommon> updatePersonalApplication(@RequestBody UpdatePersonalAppEmployeeForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try {

            if(updateForm==null){
                message="Đơn gửi đang trống!";
            }
            checkUpdate = personalLeaveApplicationService.UpdateLeaveApplication(updateForm);
            if(checkUpdate==true){
                message="Chỉnh sửa đơn gửi thành công!";
            }else{
                message="Chỉnh sửa đơn gửi không thành công!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deletePersonalApplication(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {

            if(id==null){
                message="ID null!";
            }
            checkDelete = personalLeaveApplicationService.DeleteLeaveApplication(id);
            if(checkDelete==true){
                message="Xóa đơn gửi thành công!";
            }else{
                message="Xóa sửa đơn gửi không thành công!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
