package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.AddAttendanceForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.service.SalaryService;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
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
            message = "Get Salary successfully";
            if (total.intValue() == 0) {
                message = "Get Salary not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get Salary !";
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<ResponseCommon> salaryAccept(@Valid @RequestBody SearchSalaryForm searchSalaryForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        Integer total = 0;
        try {
            searchSalaryVOS = salaryService.searchSalaryHistory(searchSalaryForm);
            total = salaryService.getTotalAllSalaryHistory(searchSalaryForm);
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setTotal(total);
            message = "Get Salary successfully";
            if (total.intValue() == 0) {
                message = "Get Salary not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get Salary !";
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
