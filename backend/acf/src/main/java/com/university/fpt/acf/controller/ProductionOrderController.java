package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.ProductionOrderService;
import com.university.fpt.acf.vo.GetCreateContactVO;
import com.university.fpt.acf.vo.ProductVO;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;
import com.university.fpt.acf.vo.SearchProductionOrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/productionorder")
public class ProductionOrderController {
    @Autowired
    private ProductionOrderService productionOrderService;
    //************************************
    // Add product for contract
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addProductIncontact(@Valid @RequestBody AddProductionOrderFrom addProductionOrderFrom) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productionOrderService.addProductionOrder(addProductionOrderFrom);
            if (addProductionOrderFrom.getId() != null) {
                message = "Sửa lệnh sản xuất thành công";
            }else{
                message = "Thêm lệnh sản xuất thành công";
            }
            if (!check) {
                if (addProductionOrderFrom.getId() != null) {
                    message = "Sửa lệnh sản xuất không thành công";
                }else{
                    message = "Thêm lệnh sản xuất không thành công";
                }
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể thêm lệnh sản xuất";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Update product of contract
    //************************************
    @PostMapping("/update")
    public ResponseEntity<ResponseCommon> updateProductIncontact(@Valid @RequestBody AddProductionOrderFrom addProductionOrderFrom) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productionOrderService.updateProductionOrder(addProductionOrderFrom);
            message = "Sửa lệnh sản xuất thành công";
            if (!check) {
                message = "Sửa lệnh sản xuất không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể sửa lệnh sản xuất";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // View working employee
    //************************************
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
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Search productionOrder  with combination of fields: contractName, nameProduction, date,status
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchProductionOrder(@RequestBody SearchProductionOrderForm searchForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<SearchProductionOrderVO> list = new ArrayList<>();
        try {
            list = productionOrderService.searchProductionOrder(searchForm);
            total = productionOrderService.totalSearchProductionOrder(searchForm);
            responseCommon.setData(list);
            message = "Thành công";
            if (total == 0) {
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
    //************************************
    // Get detail productionOrder  by idProduct
    //************************************
    @GetMapping("/getdetailproductionorder")
    public ResponseEntity<ResponseCommon> getProductInContact(@RequestParam("idproduction") Long idProduction) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ProductionOrderDetailVO> productionOrderDetailVOS = new ArrayList<>();
        try {
            productionOrderDetailVOS = productionOrderService.getDetailProduction(idProduction);
            responseCommon.setData(productionOrderDetailVOS);
            responseCommon.setTotal(1);
            message = "Xem lệnh sản xuất thành công";
            if (productionOrderDetailVOS.size() == 0) {
                responseCommon.setTotal(0);
                message = "Không xem được lệnh sản xuát";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được sản phẩm";
            responseCommon.setData(productionOrderDetailVOS);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Confirm working
    //************************************
    @PostMapping("/confirm/{id}")
    public ResponseEntity<ResponseCommon> confirmWork(@PathVariable("id") Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productionOrderService.confirmWorkDone(id);
            message = "Xác nhận hoàn thành công việc thành công";
            if (!check) {
                message = "Xác nhận hoàn thành công việc không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể xác nhận hoàn thành công việc";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Delete production order
    //************************************
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseCommon> deleteProductionOrder(@PathVariable("id") Long idProduction) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productionOrderService.deleteProductionOrder(idProduction);
            message = "Xóa lệnh sản xuất thành công";

            if (!check) {
                message = "Xóa lệnh sản xuất không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không xóa được lệnh sản xuất";
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
