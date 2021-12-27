package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    //************************************
    // Search all employee with combination of fields: name,position, status
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchEmployee(@RequestBody SearchAllEmployeeForm searchAllEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<SearchEmployeeVO> getAllEmployee = new ArrayList<>();
        try {
            getAllEmployee = employeeService.searchEmployee(searchAllEmployeeForm);
            responseCommon.setData(getAllEmployee);
            message = "Search thành công";
            if(getAllEmployee.isEmpty()){
                message = "Không tìm thấy";
            }
            total = employeeService.getTotalEmployee(searchAllEmployeeForm);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllEmployee);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    @PostMapping("/searchadd")
    public ResponseEntity<ResponseCommon> searchEmployeeAdd(@RequestBody SearchAllEmployeeForm searchAllEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<SearchEmployeeVO> getAllEmployee = new ArrayList<>();
        try {
            getAllEmployee = employeeService.searchEmployeeAdd(searchAllEmployeeForm);
            responseCommon.setData(getAllEmployee);
            message = "Search thành công";
            if(getAllEmployee.isEmpty()){
                message = "Không tìm thấy";
            }
            total = employeeService.getTotalEmployeeAdd(searchAllEmployeeForm);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllEmployee);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Get all fullname of employee that employee don't account
    //************************************
    @PostMapping("/fullnameEmNotAccount")
    public ResponseEntity<ResponseCommon> GetAllFullNameNotAccount(@RequestBody SearchEmployeeForm searchEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getFullNameEmployeeNotAccount(searchEmployeeForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    //************************************
    // Get detail employee of information by id employee
    //************************************
    @GetMapping("/getEmployee")
    public ResponseEntity<ResponseCommon> GetEmployeeDetailById(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        EmployeeDetailVO em = new EmployeeDetailVO();
        try{
            em = employeeService.getEmployeeDetailById(id);
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
    // Search all cover sheet with combination of fields: name
    //************************************
    @PostMapping("/getAllEmployee")
    public ResponseEntity<ResponseCommon> GetEmployeesNotDelete( @RequestBody SearchEmployeeForm employeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(employeeService.getEmployeeSNotDelete(employeeForm));
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }
    //************************************
    // Add employee
    //************************************
    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addEmployee(@RequestBody AddEmployeeForm addEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = employeeService.AddEmployee(addEmployeeForm);
            if(checkAdd==false){
                message="Thêm nhân viên không thành công";
            }else{
                message="Thêm nhân viên không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Update employee
    //************************************
    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updateEmployee(@Valid @RequestBody UpdateEmployeeForm updateEmployeeForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            checkUpdate =employeeService.UpdateEmployee(updateEmployeeForm);
            if(checkUpdate==false){
                message="Chỉnh sửa không thành công";
            }else{
                message="Chỉnh sửa thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Delete employee
    //************************************
    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deleteEmployee(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = employeeService.DeleteEmployee(id);
            if(checkDelete==false){
                message="Xóa nhân viên không thành công";
            }else{
                message="Xóa nhân viên thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
}
