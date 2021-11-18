package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.service.ProductionOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(path = "/admin/productionorder")
public class ProductionOrderController {
    @Autowired
    private ProductionOrderService productionOrderService;

    @PostMapping("/viewworkemployee")
    public ResponseEntity<ResponseCommon> viewWorkEmployee(@Valid @RequestBody DateWorkEmployeeFrom dateWorkEmployeeFrom) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        HashMap<String, Object> data = new HashMap<>();
        try {
            data = productionOrderService.viewWorkEmployee(dateWorkEmployeeFrom);
            message = "Lấy danh sách công việc thành công";

            if (data.size() == 0) {
                message = "Lấy danh sách công việc không thành công";
            }

            responseCommon.setData(data);
            responseCommon.setTotal(data.size());
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được hợp đồng chưa bàn giao";
            responseCommon.setData(data);
            responseCommon.setTotal(data.size());
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
