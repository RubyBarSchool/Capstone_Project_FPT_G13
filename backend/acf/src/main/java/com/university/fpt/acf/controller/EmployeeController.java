package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.GetAllAccountForm;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.form.SearchEmployeeForm;
import com.university.fpt.acf.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/spadmin/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping
    public ResponseEntity<ResponseCommon> getAllEmployee(@RequestBody SearchEmployeeForm searchEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getAllEmployee(searchEmployeeForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PostMapping("/fullnameEmNotAccount")
    public ResponseEntity<ResponseCommon> GetAllFullNameNotAccount(@RequestBody SearchEmployeeForm searchEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getFullNameEmployeeNotAccount(searchEmployeeForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
}
