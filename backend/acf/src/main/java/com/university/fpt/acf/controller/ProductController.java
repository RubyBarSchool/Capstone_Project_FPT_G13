package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.service.ProductService;
import com.university.fpt.acf.vo.ContactVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/addproduct")
    public ResponseEntity<ResponseCommon> addProductIncontact(@Valid @RequestBody AddProductForm addProductForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productService.addProductInContact(addProductForm);
            message = "Thêm sản phẩm thành công";

            if (check) {
                message = "Thêm sản phẩm không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được hợp đồng chưa bàn giao";
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
