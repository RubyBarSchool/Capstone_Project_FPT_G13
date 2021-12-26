package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddContactMoneyForm;
import com.university.fpt.acf.form.SearchContactMoneyForm;
import com.university.fpt.acf.form.SearchWorkEmployeeForm;
import com.university.fpt.acf.service.ContactMoneyService;
import com.university.fpt.acf.vo.ViewWorkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/contactmoney")
public class ContactMoneyController {
    @Autowired
    private ContactMoneyService contactMoneyService;
    //************************************
    // Search contract money with combination of fields: nameContact,statusDone
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchContactMoney(@Valid @RequestBody SearchContactMoneyForm searchContactMoneyForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<HashMap<String,Object>> contactMoney = new ArrayList<>();
        Integer total = 0;
        try {
            contactMoney = contactMoneyService.searchContactMoney(searchContactMoneyForm);
            total = contactMoneyService.getTotalSearchContactMoney(searchContactMoneyForm);
            message = "Lấy danh sách ứng tiền hợp đồng thành công";
            if (total.intValue() == 0) {
                message = "Lấy danh sách ứng tiền hợp đồng không thành công";
            }
            responseCommon.setData(contactMoney);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể lấy danh sách ứng tiền hợp đồng";
            responseCommon.setData(contactMoney);
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Add contract money
    //************************************
    @PostMapping()
    public ResponseEntity<ResponseCommon> addContactMoney(@Valid @RequestBody AddContactMoneyForm addContactMoneyForm ) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = contactMoneyService.addContactMoney(addContactMoneyForm);
            message = "Thêm lịch sử tạm ứng thành công";
            if (!check) {
                message = "Thêm lịch sử tạm ứng không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể thêm lịch sử tạm ứng";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Edit contract money
    //************************************
    @PutMapping()
    public ResponseEntity<ResponseCommon> editContactMoney(@Valid @RequestBody AddContactMoneyForm addContactMoneyForm ) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = contactMoneyService.editContactMoney(addContactMoneyForm);
            message = "Sửa lịch sử tạm ứng thành công";
            if (!check) {
                message = "Sửa lịch sử tạm ứng không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể sửa lịch sử tạm ứng!";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Delete contract money
    //************************************
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseCommon> deleteContactMoney(@PathVariable Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = contactMoneyService.deleteContactMoney(id);
            message = "xóa lịch sử tạm ứng thành công";
            if (!check) {
                message = "xóa lịch sử tạm ứng không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể xáo lịch sử tạm ứng";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Confirm contract money
    //************************************
    @PostMapping("/confirm")
    public ResponseEntity<ResponseCommon> confirmContactMoney(@RequestBody AddContactMoneyForm addContactMoneyForm ) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean check = false;
        Integer total = 1;
        try {
            check = contactMoneyService.confirmContactMoney(addContactMoneyForm);
            message = "Thêm lịch sử tạm ứng thành công";
            if (!check) {
                message = "Thêm lịch sử tạm ứng không thành công";
                total = 0;
            }
            responseCommon.setData(check);
            responseCommon.setTotal(total);
            responseCommon.setMessage(message);
            responseCommon.setStatus(HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể thêm lịch sử tạm ứng";
            responseCommon.setData(check);
            responseCommon.setTotal(0);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
}
