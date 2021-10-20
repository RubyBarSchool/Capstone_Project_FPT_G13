package com.university.fpt.acf.controller;


import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddAccountForm;
import com.university.fpt.acf.form.EmployeeNotAttendanceForm;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.GetAllAccountResponseVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/attendances")
public class AttendancesController {

    @Autowired
    private EmployeeService employeeService;

//    @PostMapping(path = "/search")
//    public ResponseEntity<ResponseCommon> searchAttendances(@RequestBody AttendanceFrom attendanceFrom){
//        ResponseCommon responseCommon = new ResponseCommon();
//        String message = "";
//        List<GetAllAccountResponseVO> getAllAccountResponseVOS = new ArrayList<>();
//        Integer total = 0;
//        try {
//            getAllAccountResponseVOS = accountService.searchAccount(searchAccountForm);
//            total = accountService.getTotalSearchAccount(searchAccountForm);
//            responseCommon.setData(getAllAccountResponseVOS);
//            responseCommon.setTotal(total);
//            message = "Get accounts successfully";
//            if(getAllAccountResponseVOS.isEmpty()){
//                message = "Get accounts not found";
//            }
//            responseCommon.setStatus(HttpStatus.OK.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
//        } catch (Exception e) {
//            message = "Could not get accounts !";
//            responseCommon.setData(getAllAccountResponseVOS);
//            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
//        }
//    }


//    @PostMapping("/add")
//    public  ResponseEntity<ResponseCommon> addAccount(@RequestBody AddAccountForm addAccountForm){
//        ResponseCommon responseCommon = new ResponseCommon();
//        responseCommon.setData(accountService.insertAccount(addAccountForm));
//        responseCommon.setStatus(HttpStatus.OK.value());
//        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
//    }


    @PostMapping(path = "/getemployee")
    public ResponseEntity<ResponseCommon> searchAttendances(@RequestBody EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<GetAllEmployeeVO> employeeVOS = new ArrayList<>();
        Integer total = 0;
        try {
            employeeVOS = employeeService.getAllEmployeeNotAttendance(employeeNotAttendanceForm);
            total = employeeService.getTotalEmployeeNotAttendance(employeeNotAttendanceForm);
            responseCommon.setData(employeeVOS);
            responseCommon.setTotal(total);
            message = "Get Employee not attendances successfully";
            if (total.intValue()==0) {
                message = "Get Employee not attendances not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get Employee not attendances !";
            responseCommon.setData(employeeVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }


}
