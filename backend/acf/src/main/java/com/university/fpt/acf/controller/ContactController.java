package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.service.ContactService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private AttendancesService attendancesService;
    //************************************
    // Add contract
    //************************************
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
            message = e.getMessage();
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Update contract
    //************************************
    @PutMapping()
    public ResponseEntity<ResponseCommon> updateContact(@Valid @RequestBody UpdateContractForm updateContactForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean result = false;
        try {
            result = contactService.updateContact(updateContactForm);
            if (result == false) {
                message = " Chỉnh sửa hợp đồng lỗi";
            }
            message = "Chỉnh sửa hợp đồng thành công";
            responseCommon.setData(result);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Chỉnh sửa hợp đồng lỗi";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Delete contract
    //************************************
    @DeleteMapping()
    public ResponseEntity<ResponseCommon> deleteContact(@RequestParam Long id) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        Boolean result = false;
        try {
            result = contactService.deleteContact(id);
            if (result == false) {
                message = "Xóa hợp đồng không thành công";
            }
            message = "Xóa hợp đồng thành công";
            responseCommon.setData(result);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Xóa hợp đồng không thành công";
            responseCommon.setData(false);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Get all contract
    //************************************
    @PostMapping("/getcontact")
    public ResponseEntity<ResponseCommon> getContactInFormSearch() {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<ContactProductionVO> contacts = new ArrayList<>();
        try {
            contacts = contactService.searchContactProduction();
            responseCommon.setData(contacts);
            responseCommon.setTotal(contacts.size());
            message = "Lấy hợp đồng thành công";
            if (contacts.size() == 0) {
                message = "Không tìm thấy hợp đồng";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thêm được hợp đồng";
            responseCommon.setData(contacts);
            responseCommon.setTotal(contacts.size());
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Read file excel
    //************************************
    @PostMapping("/readexcel")
    public ResponseEntity<ResponseCommon> readFileContact(@RequestParam("file") MultipartFile file) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        FileContactVO addAccountForm = new FileContactVO();
        try {
            addAccountForm = contactService.readFileContact(file);
            message = "Đọc tệp hợp đồng thành công";
            if (addAccountForm != null && addAccountForm.getPriceContact().equals("0")) {
                message = "Đọc tệp hợp đồng không thành công";
            }
            responseCommon.setData(addAccountForm);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể đọc được tệp hợp đồng";
            responseCommon.setData(addAccountForm);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Search contract with combination of fields: name, date, company
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchCreateContact(@RequestBody SearchCreateContactFrom searchForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<GetCreateContactVO> list = new ArrayList<>();
        try {
            list = contactService.searchCreateContact(searchForm);
            total = contactService.totalSearchCreateContact(searchForm);
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
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Export file excel contract
    //************************************
    @PostMapping("/exportcontact")
    public ResponseEntity<Resource> exportContact(@RequestBody ExportContactForm exportContactForm) {
        try {
            ByteArrayInputStream file = contactService.exportContact(exportContactForm.getId());
            String filename = "tutorials.xlsx";
            InputStreamResource filex = new InputStreamResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(filex);
        } catch (Exception exception) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "null" + "")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(null);
        }
    }
    //************************************
    // create template file contract
    //************************************
    @PostMapping("/templatecontact")
    public ResponseEntity<Resource> templateContact(@RequestBody ExportContactForm exportContactForm) {
        try {
            ByteArrayInputStream file = contactService.templateContact(exportContactForm.getId());
            String filename = "tutorials.xlsx";
            InputStreamResource filex = new InputStreamResource(file);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(filex);
        } catch (Exception exception) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + "null" + "")
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(null);
        }
    }


}
