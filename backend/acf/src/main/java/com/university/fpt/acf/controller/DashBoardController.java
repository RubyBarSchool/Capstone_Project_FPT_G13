package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.service.DashBoardService;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.DashboardAdmin;
import com.university.fpt.acf.vo.DashboardEmployee;
import com.university.fpt.acf.vo.MaterialVO;
import com.university.fpt.acf.vo.ReportTopEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/dashboard")
public class DashBoardController {
    @Autowired
    private DashBoardService dashBoardService;
    //************************************
    // Get number employee that don't have account
    //************************************
    @PostMapping("/spadmin/getnumberemployeehavenotaccount")
    public ResponseEntity<ResponseCommon> countEmployeeHaveNotAccount(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        Integer output = 0;
        try {
            output = dashBoardService.countEmployeeHaveNotAccount();
            responseCommon.setData(output);
            total= output;
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
            responseCommon.setData(output);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Get count dashboard admin
    //************************************

    @PostMapping("/admin/countdashboardadmin")
    public ResponseEntity<ResponseCommon> countDashboardAdmin(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        DashboardAdmin dashboardAdmin = new DashboardAdmin();
        try {
            dashboardAdmin = dashBoardService.getDataDashboardAdmin();
            responseCommon.setData(dashboardAdmin);
            total= 1;
            message = "Thành công";
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(dashboardAdmin);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    //************************************
    // Get count dashboard employee
    //************************************
    @PostMapping("/employee/countdashboardemployee")
    public ResponseEntity<ResponseCommon> countDashboardEmployee(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        DashboardEmployee dashboardEmployee = new DashboardEmployee();
        try {
            dashboardEmployee = dashBoardService.getDataDashboardEmployee();
            responseCommon.setData(dashboardEmployee);
            total= 1;
            message = "Thành công";
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(dashboardEmployee);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    //Report contract
    //************************************

    @PostMapping("/admin/reportContact")
    public ResponseEntity<ResponseCommon> reportContact(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<HashMap<String,Object>> dashboardEmployee = new ArrayList<>();
        try {
            dashboardEmployee = dashBoardService.getDataReportContact();
            responseCommon.setData(dashboardEmployee);
            total= dashboardEmployee.size();
            message = "Thành công";
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(dashboardEmployee);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Get top employee
    //************************************
    @PostMapping("/admin/topemployee")
    public ResponseEntity<ResponseCommon> getTopEmployee(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<ReportTopEmployeeVO> dashboardEmployee = new ArrayList<>();
        try {
            dashboardEmployee = dashBoardService.getTopEmployee();
            responseCommon.setData(dashboardEmployee);
            total= dashboardEmployee.size();
            message = "Thành công";
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(dashboardEmployee);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }


}
