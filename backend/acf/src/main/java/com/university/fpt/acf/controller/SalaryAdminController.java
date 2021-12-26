package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.service.SalaryService;
import com.university.fpt.acf.vo.SearchSalaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/salary")
public class SalaryAdminController {

    @Autowired
    private SalaryService salaryService;

    //************************************
    // Get history salary by name, position, date
    //************************************
    @PostMapping("/history")
    public ResponseEntity<ResponseCommon> salaryHistory(@Valid @RequestBody SearchSalaryForm searchSalaryForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        Integer total = 0;
        try {
            searchSalaryVOS = salaryService.searchSalaryHistory(searchSalaryForm);
            total = salaryService.getTotalAllSalaryHistory(searchSalaryForm);
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setTotal(total);
            message = "Thành công";
            if (total.intValue() == 0) {
                message = "Không tìm thấy";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Accept salary
    //************************************
    @PostMapping("/accept")
    public ResponseEntity<ResponseCommon> salaryAccept(@Valid @RequestBody SearchSalaryForm searchSalaryForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        Integer total = 0;
        try {
            searchSalaryVOS = salaryService.searchSalaryAccept(searchSalaryForm);
            total = salaryService.getTotalAllSalaryAccept(searchSalaryForm);
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setTotal(total);
            message = "Thành công";
            if (total.intValue() == 0) {
                message = "Không tìm thấy";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Accept salary done
    //************************************
    @PutMapping("/accept/{id}")
    public ResponseEntity<ResponseCommon> salaryAcceptDone(@PathVariable Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean status = false;
        try {
            status = salaryService.acceptSalary(id);
            responseCommon.setData(status);
            responseCommon.setTotal(0);
            message = "Duyệt ứng lương thành công";
            if (!status) {
                message = "Duyệt ứng lương lỗi";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(status);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
