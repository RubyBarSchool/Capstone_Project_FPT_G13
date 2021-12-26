package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.UpdateBonusForm;
import com.university.fpt.acf.service.PunishService;
import com.university.fpt.acf.vo.ResultSearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/punish")
public class PunishController {
    @Autowired
    private PunishService punishService;
    //************************************
    // Search punish  with combination of fields: title, date,status
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchPunish(@RequestBody SearchBonusAdminForm searchForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<ResultSearchBonusAdminVO> listResult = new ArrayList<>();
        try {
            listResult = punishService.searchPunish(searchForm);
            total = punishService.totalSearchPunish(searchForm);
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
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Add punish
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addPunish(@RequestBody AddBonusAdminForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {

            if(addForm==null){
                message="Thông tin đang chống";
            }
            checkAdd = punishService.addPunish(addForm);
            if(checkAdd==true){
                message="Thêm đơn phạt thành công";
            }else{
                message="Thêm đơn phạt lỗi";
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
    // Delete punish
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deletePunish(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = punishService.deletePunish(id);
            if(checkDelete==true){
                message="Hủy đơn phạt thành công";
            }else{
                message="Hủy đơn phạt không thành công";
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
    //Update punish
    //************************************
    @PutMapping("/update")
    public ResponseEntity<ResponseCommon> updatePunish(@RequestBody UpdateBonusForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = punishService.updatePunish(updateForm);
            if(checkDelete==true){
                message="Chỉnh sửa đơn phạt thành công";
            }else{
                message="Chỉnh sửa đơn phạt không thành công";
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
