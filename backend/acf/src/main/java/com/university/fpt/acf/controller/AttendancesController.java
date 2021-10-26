package com.university.fpt.acf.controller;


import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.GetAllAccountResponseVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import com.university.fpt.acf.vo.ResponsePriviewExcel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @PostMapping
    public ResponseEntity<ResponseCommon> addAttendance(@Valid @RequestBody AddAttendanceForm addAccountForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            List<TimeKeep> timeKeeps = attendancesService.saveAttendance(addAccountForm);
            message = "add attendances successfully";
            if (timeKeeps.size() == 0) {
                message = "add false";
                responseCommon.setData(false);
            }
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

    @PutMapping
    public ResponseEntity<ResponseCommon> updateAttendance(@Valid @RequestBody UpdateAttendanceForm updateAttendanceForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        try {
            TimeKeep timeKeeps = attendancesService.updateAttendance(updateAttendanceForm);
            message = "update attendances successfully";
            if (timeKeeps.getId() == null) {
                message = "update false";
                responseCommon.setData(false);
            }
            responseCommon.setData(true);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not update attendances !";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }


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
            message = "Could not get Employee not attendances !";
            responseCommon.setData(employeeVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

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
            message = "Get attendances successfully";
            if (total == 0) {
                message = "Get attendances not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get  attendances !";
            responseCommon.setData(attendanceVOS);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PostMapping(path = "/priview")
    public ResponseEntity<ResponsePriviewExcel> priviewExcel(@Valid @RequestBody ExportExcelForm exportExcelForm) {
        ResponsePriviewExcel excel = new ResponsePriviewExcel();
        String message = "";
        List<Object> priviewExcel = new ArrayList<>();
        List<Object> nameSheets = new ArrayList<>();
        try {
            priviewExcel = attendancesService.priviewExcel(exportExcelForm);
            nameSheets.addAll((Collection<?>) priviewExcel.get(priviewExcel.size() - 1));
            priviewExcel.remove(priviewExcel.size() - 1);
            excel.setData(priviewExcel);
            excel.setNameSheets(nameSheets);
            message = "Get attendances successfully";
            return ResponseEntity.status(HttpStatus.OK).body(excel);
        } catch (Exception e) {
            message = "Could not get  attendances !";
            excel.setData(priviewExcel);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(excel);
        }
    }


}
