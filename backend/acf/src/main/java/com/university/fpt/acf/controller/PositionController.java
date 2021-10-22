package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.PositionService;
import com.university.fpt.acf.vo.GetAllRoleVO;
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
            message = "Get position successfully";
            if(total==0){
                message = "Get position not found";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllPositionVOList);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/add")
    public  ResponseEntity<ResponseCommon> addPosition(@RequestBody AddPositionForm addPositionForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            if(addPositionForm.getName()==null || addPositionForm.getName()==""){
                message="Data is null! Dont Add!";
            }else {
                checkAdd =positionService.addPosition(addPositionForm);
                if(checkAdd==false){
                    message="Add position fail!";
                }else{
                    message="Add position sucessfuly!";
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

    @PutMapping("/update")
    public  ResponseEntity<ResponseCommon> updatePosition(@RequestBody UpdatePositionForm updatePositionForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try{
            if(updatePositionForm.getName()==null || updatePositionForm.getName()==""){
                message="Data is emplty! Dont Add!";
            }else {
                checkUpdate =positionService.updatePosition(updatePositionForm);
                if(checkUpdate==false){
                    message="Update position fail!";
                }else{
                    message="Update position sucessfuly!";
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
    @DeleteMapping("/delete")
    public  ResponseEntity<ResponseCommon> deleteAccount(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try{
            checkDelete = positionService.deletePosition(id);
            if(checkDelete==false){
                message="Delete position fail!";
            }else{
                message="Delete position sucessfuly";
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
