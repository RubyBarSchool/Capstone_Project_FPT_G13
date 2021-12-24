package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.vo.ResultSearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/bonus")
    public class BonusController {
    @Autowired
    private BonusService bonusService;
    //************************************
    // Search all bonus for employee with combination of fields:title,date,status
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchBonus(@RequestBody SearchBonusAdminForm searchForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<ResultSearchBonusAdminVO> listResult = new ArrayList<>();
        try {
            listResult = bonusService.searchBonus(searchForm);
            total = bonusService.totalSearchBonus(searchForm);
            responseCommon.setData(listResult);
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
            responseCommon.setData(listResult);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Add bonus for employee
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addBonus(@RequestBody AddBonusAdminForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {

            if(addForm==null){
                message="Thông tin đang trống";
            }
            checkAdd = bonusService.addBonus(addForm);
            if(checkAdd==true){
                message="Thêm đơn thưởng thành công";
            }else{
                message="Thêm đơn thưởng lỗi";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Delete bonus for employee
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteBonus(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = bonusService.deleteBonus(id);
            if(checkDelete==true){
                message="Hủy đơn  thành công";
            }else{
                message="Hủy đơn không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Update bonus for employee
    //************************************
    @PutMapping("/update")
    public ResponseEntity<ResponseCommon> updateBonus(@RequestBody UpdateBonusForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = bonusService.updateBonus(updateForm);
            if(checkDelete==true){
                message="Chỉnh sửa đơn thưởng thành công";
            }else{
                message="Chỉnh sửa đơn thưởng không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}
