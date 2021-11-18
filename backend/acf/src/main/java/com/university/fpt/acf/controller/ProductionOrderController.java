package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.form.SearchCreateContactFrom;
import com.university.fpt.acf.form.SearchProductionOrderForm;
import com.university.fpt.acf.service.ProductionOrderService;
import com.university.fpt.acf.vo.GetCreateContactVO;
import com.university.fpt.acf.vo.SearchProductionOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchProductOrder(@RequestBody SearchProductionOrderForm searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<SearchProductionOrderVO> list = new ArrayList<>();
        try {
            list = productionOrderService.searchProductionOrder(searchForm);
            total = productionOrderService.totalSearchProductionOrder(searchForm);
            responseCommon.setData(list);
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
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
}
