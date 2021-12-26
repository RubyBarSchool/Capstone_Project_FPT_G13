package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.CompanyService;
import com.university.fpt.acf.util.EmployeeValidate.EmployeeValidate;
import com.university.fpt.acf.vo.CompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    //************************************
    // Search company with combination of fields: name, address, phone
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchCompany(@RequestBody SearchCompanyForm searchCompanyForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<CompanyVO> listCompany = new ArrayList<>();
        try {
            listCompany = companyService.searchCompany(searchCompanyForm);
            total = companyService.getTotalSearchCompany(searchCompanyForm);
            responseCommon.setData(listCompany);
            message = "Thành công";
            if(total==0){
                message = "Không tìm thấy";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listCompany);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Add company
    //************************************
    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addCompany(@RequestBody AddCompanyForm addCompanyForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean checkAdd = false;
        try {
            if (addCompanyForm.getName() == null || addCompanyForm.getName() == "") {
                message = "Dữ liệu bị NULL";
            }
            EmployeeValidate employeeValidate = new EmployeeValidate();
            if(employeeValidate.checkFormEmail(addCompanyForm.getEmail())==false){
                checkAdd = false;
                message ="Email không đúng định dạng";
            }else {
                checkAdd = companyService.insertCompany(addCompanyForm);
                if (checkAdd == false) {
                    message = "Thêm không thành công";
                } else {
                    message = "Thêm thành công";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Update company
    //************************************
    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updateCompany(@RequestBody UpdateCompanyForm updateCompanyForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            if(updateCompanyForm.getName()==null || updateCompanyForm.getName()==""){
                message="Tên công ty trống";
            }
            EmployeeValidate employeeValidate = new EmployeeValidate();

            if(!employeeValidate.checkFormEmail(updateCompanyForm.getEmail())){
                checkUpdate =false;
                message ="Không đúng định dạng email";
            }else {
                checkUpdate = companyService.updateCompany(updateCompanyForm);
                if (checkUpdate == false) {
                    message = "Chỉnh sửa công ty không thành công";
                } else {
                    message = "Chỉnh sửa công ty thành công";
                }
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
    //************************************
    // Delete company
    //************************************
    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deleteCompany(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = companyService.deleteCompany(id);
            if(checkDelete==false){
                message="Xóa không thành công";
            }else{
                message="Xóa thành công";
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
