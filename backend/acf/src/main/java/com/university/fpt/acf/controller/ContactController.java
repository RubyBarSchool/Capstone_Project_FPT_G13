package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.form.AddContactForm;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.service.ContactService;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.FileContactVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping()
    public ResponseEntity<ResponseCommon> addContact(@Valid @RequestBody AddContactForm addContactForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Contact contact = new Contact();
        Boolean result = true;
        try {
            contact = contactService.addContact(addContactForm);
            message = "Thêm hợp đồng thành công";
            if (contact == null) {
                message = "Không thêm được hợp đồng";
                result = false;
            }
            responseCommon.setData(result);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được hợp đồng";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PostMapping("/getContactInSearch")
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

    @PostMapping("/readexcel")
    public ResponseEntity<ResponseCommon> readFileContact(@RequestParam("file") MultipartFile file) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        FileContactVO addAccountForm = new FileContactVO();
        try {
            addAccountForm = contactService.readFileContact(file);
            message = "Đọc tệp hợp đồng thành công";
            if (addAccountForm != null && addAccountForm.getPriceContact().equals("0")) {
                message = "Đọc tệp hợp đồng không thành công!";
            }
            responseCommon.setData(addAccountForm);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể đọc được tệp hợp đồng!";
            responseCommon.setData(addAccountForm);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }


}
