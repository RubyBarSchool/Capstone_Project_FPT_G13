package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.service.GroupMaterialService;
import com.university.fpt.acf.vo.GroupMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/groupmaterial")
public class GroupMaterialController {
    @Autowired
    private GroupMaterialService service;
    //************************************
    // Get all group material
    //************************************
    @GetMapping("/get")
    public ResponseEntity<ResponseCommon> getAllGroupS(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GroupMaterialVO> list = new ArrayList<>();
        try {
            list = service.getAllGroupMaterial();
            responseCommon.setData(list);
            total=list.size();
            message = "Thành công";
            if(total==0){
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
    // Add group material
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addGroup(@RequestParam String name){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = service.addGroupMaterial(name);
            if(checkAdd==true){
                message="Thêm thành công";
            }else{
                message="Thêm không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAdd);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Delete group material by id
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteGroup(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = service.deleteGroupMaterial(id);
            if(checkDelete==true){
                message="Xóa thành công";
            }else{
                message="Xóa không thành công";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
}
