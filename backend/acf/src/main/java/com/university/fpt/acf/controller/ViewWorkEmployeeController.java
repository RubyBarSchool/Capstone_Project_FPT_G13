package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.SearchWorkEmployeeForm;
import com.university.fpt.acf.form.UpdateWorkEmployeeFrom;
import com.university.fpt.acf.service.ProductionOrderService;
import com.university.fpt.acf.vo.ViewWorkDetailVO;
import com.university.fpt.acf.vo.ViewWorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/employee/viewwork")
public class ViewWorkEmployeeController {

    @Autowired
    private ProductionOrderService productionOrderService;
    //************************************
    // Search working employee by status
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchWorkEmployee(@Valid @RequestBody SearchWorkEmployeeForm searchWorkEmployeeForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ViewWorkVO> ViewWorkVOS = new ArrayList<>();
        Integer total = 0;
        try {
            ViewWorkVOS = productionOrderService.searchProductionOrderEmployee(searchWorkEmployeeForm);
            total = productionOrderService.totalSearchProductionOrderEmployee(searchWorkEmployeeForm);
            responseCommon.setData(ViewWorkVOS);
            responseCommon.setTotal(total);
            message = "Lấy sanh sách công việc thành công";
            if (total.intValue() == 0) {
                message = "Lấy danh sách công việc không thành công";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể lấy danh sách công việc";
            responseCommon.setData(ViewWorkVOS);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Search working employee  by id
    //************************************
    @PostMapping("/{id}")
    public ResponseEntity<ResponseCommon> searchWorkEmployee(@PathVariable("id") Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ViewWorkDetailVO> viewWorkDetailVOS = new ArrayList<>();
        try {
            viewWorkDetailVOS = productionOrderService.searchProductionOrderDetailEmployee(id);
            responseCommon.setData(viewWorkDetailVOS);
            responseCommon.setTotal(viewWorkDetailVOS.size());
            message = "Lấy danh sách vật tư trong công việc thành công";
            if (viewWorkDetailVOS.size() == 0) {
                message = "Lấy danh sách vật tư trong công việc không thành công";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể lấy danh sách vật tư trong công việc";
            responseCommon.setData(viewWorkDetailVOS);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Confirm work
    //************************************
    @PostMapping("/confirm/{id}")
    public ResponseEntity<ResponseCommon> confirmWork(@PathVariable("id") Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productionOrderService.confirmWork(id);
            message = "Xác nhận công việc thành công";
            if (!check) {
                message = "Xác nhận công việc không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể xác nhận công việc";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Update working employee
    //************************************
    @PutMapping()
    public ResponseEntity<ResponseCommon> updateWorkEmployee(@Valid @RequestBody UpdateWorkEmployeeFrom updateWorkEmployeeFrom) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productionOrderService.updateWork(updateWorkEmployeeFrom);
            message = "Xác nhận công việc thành công";
            if (!check) {
                message = "Xác nhận công việc không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể xác nhận công việc";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

}
