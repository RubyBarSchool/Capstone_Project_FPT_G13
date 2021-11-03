package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.AddAttendanceForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/salary")
public class SalaryAdminController {

//    @PutMapping
//    public ResponseEntity<ResponseCommon> acceptSalary(@Valid @RequestBody SearchSalaryForm searchSalaryForm) {
//        ResponseCommon responseCommon = new ResponseCommon();
//        String message = "";
//        try {
//            List<TimeKeep> timeKeeps = attendancesService.saveAttendance(addAccountForm);
//            message = "add attendances successfully";
//            if (timeKeeps.size() == 0) {
//                message = "add false";
//                responseCommon.setData(false);
//            }
//            responseCommon.setData(true);
//            responseCommon.setStatus(HttpStatus.OK.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
//        } catch (Exception e) {
//            message = "Could not add attendances !";
//            responseCommon.setData(false);
//            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
//        }
//    }
}
