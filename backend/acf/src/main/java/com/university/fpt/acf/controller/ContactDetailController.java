package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.form.SearchContactDetailForm;
import com.university.fpt.acf.service.ContactService;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.MaterialInContactDetailVO;
import com.university.fpt.acf.vo.SearchContactDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/contactdetail")
public class ContactDetailController {
    @Autowired
    private ContactService contactService;
    //************************************
    // Search all contract with fields: name
    //************************************
    @PostMapping("/getcontactinsearch")
    public ResponseEntity<ResponseCommon> getContactInFormSearch(@Valid @RequestBody ContactInSearchForm contactInSearchForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ContactVO> contacts = new ArrayList<>();
        Integer total = 0;
        try {
            contacts = contactService.searchContact(contactInSearchForm);
            total = contactService.getTotalSearchContact(contactInSearchForm);
            responseCommon.setData(contacts);
            responseCommon.setTotal(total);
            message = "Lấy hợp đồng thành công";
            if (total.intValue() == 0) {
                message = "Không tìm thấy hợp đồng";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được hợp đồng";
            responseCommon.setData(contacts);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    //************************************
    // Get all contract
    //************************************

    @PostMapping("/getcontactinadd")
    public ResponseEntity<ResponseCommon> getContactInFormAdd() {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ContactVO> contacts = new ArrayList<>();
        try {
            contacts = contactService.searchContactNotDone();
            responseCommon.setData(contacts);
            responseCommon.setTotal(contacts.size());
            message = "Lấy hợp đồng chưa bàn giao thành công";
            if (contacts.size() == 0) {
                message = "Không tìm thấy hợp đồng chưa bàn giao";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được hợp đồng chưa bàn giao";
            responseCommon.setData(contacts);
            responseCommon.setTotal(contacts.size());
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    //************************************
    // Search contract with combination of fields: contractName, nameProduct
    //************************************
    @PostMapping()
    public ResponseEntity<ResponseCommon> searchContactDetail(@Valid @RequestBody SearchContactDetailForm searchContactDetailForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<SearchContactDetailVO> searchContactDetailVOS = new ArrayList<>();
        Integer total = 0;
        try {
            searchContactDetailVOS = contactService.searchContactDetail(searchContactDetailForm);
            total = contactService.getTotalSearchContactDetail(searchContactDetailForm);
            responseCommon.setData(searchContactDetailVOS);
            responseCommon.setTotal(total);
            message = "Tìm kiếm chi tiết hợp đồng thành công";
            if (total.intValue() == 0) {
                message = "Tìm kiếm chi tiết hợp đồng không thành công";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể tìm kiếm chi tiết hợp đồng";
            responseCommon.setData(searchContactDetailVOS);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Get all material by id product
    //************************************
    @GetMapping("/getmaterial/{id}")
    public ResponseEntity<ResponseCommon> getMaterialInProduct(@PathVariable Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<MaterialInContactDetailVO> materialInContactDetailVOS = new ArrayList<>();
        try {
            materialInContactDetailVOS = contactService.getMaterialInProduct(id);
            responseCommon.setData(materialInContactDetailVOS);
            message = "Lấy vật liệu trong sản phẩm thành công";
            if (materialInContactDetailVOS.size() == 0) {
                message = "Lấy vật liệu trong sản phẩm không thành công";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể tìm kiếm chi tiết hợp đồng";
            responseCommon.setData(materialInContactDetailVOS);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

}

