package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/employee")
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
    public  ResponseEntity<ResponseCommon> updateEmployee(@RequestBody UpdateEmployeeForm updateEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            checkUpdate =employeeService.UpdateEmployee(updateEmployeeForm);
            if(checkUpdate==false){
                message="Update account fail!";
            }else{
                message="Update sucessfuly!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
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
            message = "Can't delete employee!";
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
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
