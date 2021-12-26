package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.PositionService;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/position")
public class PositionController {
    @Autowired
    private PositionService positionService;
    //************************************
    // Search position  with combination of fields: name
    //************************************
    @PostMapping
    public ResponseEntity<ResponseCommon> searchPositions(@RequestBody PositionForm positionForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<PositionResponseVO> getAllPositionVOList = new ArrayList<>();
        try {
            getAllPositionVOList = positionService.searchPosition(positionForm);
            total = positionService.totalSearchPosition(positionForm);
            responseCommon.setData(getAllPositionVOList);
            message = "Thành công!";
            if(total==0){
                message = "Không tìm thấy!";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllPositionVOList);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Add position
    //************************************
    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addPosition(@RequestBody AddPositionForm addPositionForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            if(addPositionForm.getName()==null || addPositionForm.getName()==""){
                message="Dữ liệu NULL!";
            }else {
                checkAdd =positionService.addPosition(addPositionForm);
                if(checkAdd==false){
                    message="Thêm không thành công!";
                }else{
                    message="Thêm thành công!";
                }
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
    // Update position
    //************************************
    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updatePosition(@RequestBody UpdatePositionForm updatePositionForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            if(updatePositionForm.getName()==null || updatePositionForm.getName()==""){
                message="Dữ liệu Null hoặc trống";
            }else {
                checkUpdate =positionService.updatePosition(updatePositionForm);
                if(checkUpdate==false){
                    message="Chỉnh sửa không thành công";
                }else{
                    message="Chình sửa thành công";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Delete position
    //************************************
    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deletePosition(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = positionService.deletePosition(id);
            if(checkDelete==false){
                message="Xóa không thành công";
            }else{
                message="Xóa thành công";
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
