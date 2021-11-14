package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.AddAttendanceForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/contact")
public class ContactController {


    @PostMapping("/readexcel")
    public ResponseEntity<ResponseCommon> readFileContact(@RequestParam("file") MultipartFile file) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
//            List<TimeKeep> timeKeeps = attendancesService.saveAttendance(addAccountForm);
//            message = "Thêm thành công !";
//            if (timeKeeps.size() == 0) {
//                message = "Thêm không thành công!";
//                responseCommon.setData(false);
//            }
            responseCommon.setData(true);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not add attendances !";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
