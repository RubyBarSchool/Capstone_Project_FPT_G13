package com.university.fpt.acf.controller;


import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/attendances")
public class AttendancesController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttendancesService attendancesService;

    //************************************
    // Add attendance for employee
    //************************************
    @PostMapping
    public ResponseEntity<ResponseCommon> addAttendance(@Valid @RequestBody AddAttendanceForm addAccountForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            List<TimeKeep> timeKeeps = attendancesService.saveAttendance(addAccountForm);
            message = "Thêm thành công";
            if (timeKeeps.size() == 0) {
                message = "Thêm không thành công";
                responseCommon.setData(false);
            }
            responseCommon.setData(true);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not add attendances";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Update attendance for employee
    //************************************

    @PutMapping
    public ResponseEntity<ResponseCommon> updateAttendance(@Valid @RequestBody UpdateAttendanceForm updateAttendanceForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            TimeKeep timeKeeps = attendancesService.updateAttendance(updateAttendanceForm);
            message = "Chỉnh sửa thành công";
            if (timeKeeps.getId() == null) {
                message = "update false";
                responseCommon.setData(false);
            }
            responseCommon.setData(true);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not update attendances";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    //************************************
    // Search all employee attendance for employee with combination of fields:date
    //************************************
    @PostMapping(path = "/getemployee")
    public ResponseEntity<ResponseCommon> searchAttendances(@Valid @RequestBody EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<GetAllEmployeeVO> employeeVOS = new ArrayList<>();
        Integer total = 0;
        try {
            employeeVOS = employeeService.getAllEmployeeNotAttendance(employeeNotAttendanceForm);
            total = employeeService.getTotalEmployeeNotAttendance(employeeNotAttendanceForm);
            responseCommon.setData(employeeVOS);
            responseCommon.setTotal(total);
            message = "Get Employee not attendances successfully";
            if (total.intValue() == 0) {
                message = "Get Employee not attendances not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get Employee not attendances";
            responseCommon.setData(employeeVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Search all attendance for employee with combination of fields:name,date,type,note
    //************************************
    @PostMapping(path = "/search")
    public ResponseEntity<ResponseCommon> searchAttendance(@Valid @RequestBody AttendanceFrom attendanceFrom) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<AttendanceVO> attendanceVOS = new ArrayList<>();
        int total = 0;
        try {
            attendanceVOS = attendancesService.getAllAttendance(attendanceFrom);
            total = attendancesService.getTotalAllAttendance(attendanceFrom);
            responseCommon.setData(attendanceVOS);
            responseCommon.setTotal(total);
            message = "Thành công";
            if (total == 0) {
                message = "Không tìm thấy";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get  attendances";
            responseCommon.setData(attendanceVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Preview excel
    //************************************
    @PostMapping(path = "/preview")
    public ResponseEntity<ResponseCommon> previewExcel(@Valid @RequestBody ExportExcelForm exportExcelForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<Object> priviewExcel = new ArrayList<>();
        List<Object> nameSheets = new ArrayList<>();
        try {
            priviewExcel = attendancesService.priviewExcel(exportExcelForm);
            responseCommon.setData(priviewExcel);
            message = "Thành công";
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get  attendances";
            responseCommon.setData(priviewExcel);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Download excel
    //************************************
    @PostMapping("/down")
    public ResponseEntity<Resource> downExcel( @Valid @RequestBody ExportExcelForm exportExcelForm) {
        ByteArrayInputStream file = attendancesService.downExcel(exportExcelForm);
        String filename = "tutorials.xlsx";
        InputStreamResource filex = new InputStreamResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(filex);
    }
}
