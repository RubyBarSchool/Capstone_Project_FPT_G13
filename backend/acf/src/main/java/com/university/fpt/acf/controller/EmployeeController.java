package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchEmployee(@RequestBody SearchAllEmployeeForm searchAllEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<SearchEmployeeVO> getAllEmployee = new ArrayList<>();
        try {
            getAllEmployee = employeeService.searchEmployee(searchAllEmployeeForm);
            responseCommon.setData(getAllEmployee);
            message = "Get employee successfully";
            if(getAllEmployee.isEmpty()){
                message = "Get employee not found";
            }
            total = employeeService.getTotalEmployee(searchAllEmployeeForm);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllEmployee);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/fullnameEmNotAccount")
    public ResponseEntity<ResponseCommon> GetAllFullNameNotAccount(@RequestBody SearchEmployeeForm searchEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getFullNameEmployeeNotAccount(searchEmployeeForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @GetMapping("/getEmployee")
    public ResponseEntity<ResponseCommon> GetEmployeeDetailById(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        EmployeeDetailVO em = new EmployeeDetailVO();
        try{
            em = employeeService.getEmployeeDetailById(id);
            if(em==null){
                message ="Dont find Employee";
            }
                message ="Sucessfuly";
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
    @GetMapping()
    public ResponseEntity<ResponseCommon> GetEmployeesNotDelete(){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getEmployeeSNotDelete());
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addEmployee(@RequestBody AddEmployeeForm addEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd =employeeService.AddEmployee(addEmployeeForm);
            if(checkAdd==false){
                message="Add employee fail!";
            }else{
                message="Add employee sucessfuly!";
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
    public  ResponseEntity<ResponseCommon> updateEmployee(@Valid @RequestBody UpdateEmployeeForm updateEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            checkUpdate =employeeService.UpdateEmployee(updateEmployeeForm);
            if(checkUpdate==false){
                message="Chỉnh sửa không thành công!";
            }else{
                message="Chỉnh sửa thành công!";
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
    public  ResponseEntity<ResponseCommon> deleteEmployee(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = employeeService.DeleteEmployee(id);
            if(checkDelete==false){
                message="Delete employee fail!";
            }else{
                message="Delete employee sucessfuly";
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
