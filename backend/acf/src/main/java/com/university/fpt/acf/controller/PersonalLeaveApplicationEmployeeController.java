package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.PersonalLeaveApplicationEmployeeService;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/employee/personalLeaveApplication")
public class PersonalLeaveApplicationEmployeeController {
    @Autowired
    PersonalLeaveApplicationEmployeeService personalLeaveApplicationService;
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchPersonalLeaveApplication(@RequestBody SearchPersonalApplicationEmployeeForm searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<SearchPersonalApplicationEmployeeVO> list = new ArrayList<>();
        try {
            list = personalLeaveApplicationService.searchPersonalLeaveApplicationEmployee(searchForm);
            total = personalLeaveApplicationService.totalSearch(searchForm);
            responseCommon.setData(list);
            message = "Get  Application successfully";
            if(total==0){
                message = "Get  Application not found";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
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
//    @GetMapping("/detail")
//    public ResponseEntity<ResponseCommon> getDetailPersonalLeaveApplicationEmployee(@RequestParam Long id){
//        ResponseCommon responseCommon = new ResponseCommon();
//        String message="";
//        SearchPersonalApplicationEmployeeVO data = new SearchPersonalApplicationEmployeeVO();
//        try {
//            data = personalLeaveApplicationService.detailPersonalLeaveAppEmployee(id);
//            if(data==null){
//                message="Không tìm thấy đơn nghỉ phép";
//            }else{
//                message="Lấy đơn nghỉ phép thành công!";
//            }
//            responseCommon.setMessage(message);
//            responseCommon.setData(data);
//            responseCommon.setStatus(HttpStatus.OK.value());
//            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
//        }catch (Exception e){
//            message = e.getMessage();
//            responseCommon.setData(data);
//            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
//        }
//    }

}
