package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.service.ProductService;
import com.university.fpt.acf.vo.ContactProductionVO;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    //************************************
    // Add product for contract
    //************************************
    @PostMapping("/addproduct")
    public ResponseEntity<ResponseCommon> addProductIncontact(@Valid @RequestBody AddProductForm addProductForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productService.addProductInContact(addProductForm);

            if(addProductForm.getIdProduct() != null){
                message = "Sửa sản phẩm thành công";
            }else{
                message = "Thêm sản phẩm thành công";
            }

            if (!check) {

                if(addProductForm.getIdProduct() != null){
                    message = "Sửa sản phẩm không thành công";
                }else{
                    message = "Thêm sản phẩm không thành công";
                }
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
    //************************************
    // Delete product of contract by id contract
    //************************************
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseCommon> deleteProductIncontact(@PathVariable Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = productService.deleteProductInContact(id);
            message = "Xóa sản phẩm thành công";

            if (check) {
                message = "Xóa sản phẩm không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không Xóa được sản phẩm";
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Get all product of contract by id contract
    //************************************
    @GetMapping("/getproductincontact")
    public ResponseEntity<ResponseCommon> getProductInContact(@RequestParam("idcontact") Long idContact) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ProductVO> productVOS = new ArrayList<>();
        try {
            productVOS = productService.getProductInContact(idContact);
            responseCommon.setData(productVOS);
            responseCommon.setTotal(productVOS.size());
            message = "Lấy sản phẩm thành công";
            if (productVOS.size() == 0) {
                message = "Không tìm thấy sản phẩm";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được sản phẩm";
            responseCommon.setData(productVOS);
            responseCommon.setTotal(productVOS.size());
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Get all product
    //************************************
    @GetMapping("/getproductincontactall")
    public ResponseEntity<ResponseCommon> getProductInContactAll(@RequestParam("idcontact") Long idContact) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ProductVO> productVOS = new ArrayList<>();
        try {
            productVOS = productService.getProductInContactAll(idContact);
            responseCommon.setData(productVOS);
            responseCommon.setTotal(productVOS.size());
            message = "Lấy sản phẩm thành công";
            if (productVOS.size() == 0) {
                message = "Không tìm thấy sản phẩm";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được sản phẩm";
            responseCommon.setData(productVOS);
            responseCommon.setTotal(productVOS.size());
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
