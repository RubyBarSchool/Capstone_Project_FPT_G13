package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AcceptAdvanceSalaryAdminForm;
import com.university.fpt.acf.form.SearchAdvanceSalaryAdminForm;
import com.university.fpt.acf.service.AdvanceSalaryAdminService;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/advanceSalary")
public class AdvanceSalaryAdminController {
    @Autowired
    private AdvanceSalaryAdminService adminService;
    //************************************
    // Search all advance salary with combination of fields by title, employeeName, date, accept
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchAdvanceSalaryAdmin(@RequestBody SearchAdvanceSalaryAdminForm searchForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<SearchAdvanceSalaryAdminVO> list = new ArrayList<>();
        try {
            list = adminService.searchAdvanceSalaryAdmin(searchForm);
            total = adminService.totalSearch(searchForm);
            responseCommon.setData(list);
            message = "Thành công";
            if (total == 0) {
                message = "Không tìm thấy";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Accept advance salary
    //************************************
    @PutMapping("/accept")
    public ResponseEntity<ResponseCommon> acceptAdvanceSalaryAdmin(@RequestBody AcceptAdvanceSalaryAdminForm acceptForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        try {
            check = adminService.acceptAddvanceSalary(acceptForm);
            if (check == true) {
                message = "Duyệt đơn ứng lương thành công";
            } else {
                message = "Duyệt đơn ứng lương không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(check);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(check);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Reject advance salary
    //************************************
    @PutMapping("/reject")
    public ResponseEntity<ResponseCommon> rejectAdvanceSalaryAdmin(@RequestBody AcceptAdvanceSalaryAdminForm acceptForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        try {
            check = adminService.rejectAdvanceSalary(acceptForm);
            if (check == true) {
                message = "Loại bỏ đơn ứng lương thành công";
            } else {
                message = "Loại bỏ đơn ứng lương không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(check);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon, HttpStatus.OK);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(check);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
}
