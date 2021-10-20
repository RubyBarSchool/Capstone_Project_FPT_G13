package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.GetAllAccountForm;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.form.SearchEmployeeForm;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/spadmin/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    // chua fix
//    @PostMapping
//    public ResponseEntity<ResponseCommon> getAllEmployee(@RequestBody SearchEmployeeForm searchEmployeeForm){
//        ResponseCommon responseCommon = new ResponseCommon();
//        responseCommon.setData(employeeService.getAllEmployee(searchEmployeeForm));
//        responseCommon.setStatus(HttpStatus.OK.value());
//        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
//    }
    @PostMapping("/fullnameEmNotAccount")
    public ResponseEntity<ResponseCommon> GetAllFullNameNotAccount(@RequestBody SearchEmployeeForm searchEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getFullNameEmployeeNotAccount(searchEmployeeForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }

//    @PostMapping("/getAllEmployee")
//    public ResponseEntity<ResponseCommon> GetAllEmployee(@RequestBody SearchEmployeeForm searchEmployeeForm){
//
//        ResponseCommon responseCommon = new ResponseCommon();
//        String message = "";
//        List<GetAllEmployeeVO>  employeeVOS= new ArrayList<>();
//        try {
//            employeeVOS.addAll(employeeService.getAllEmployee(searchEmployeeForm));
//            responseCommon.setData(employeeVOS);
//            responseCommon.setTotal(employeeService.getTotalEmployee(searchEmployeeForm));
//            message = "Get all Employee successfully";
//            if(employeeVOS.isEmpty()){
//                message = "Get all Employee not found";
//            }
//            responseCommon.setStatus(HttpStatus.OK.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
//        } catch (Exception e) {
//            message = "Could not get all employee !";
//            responseCommon.setData(false);
//            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
//        }
//    }
}
