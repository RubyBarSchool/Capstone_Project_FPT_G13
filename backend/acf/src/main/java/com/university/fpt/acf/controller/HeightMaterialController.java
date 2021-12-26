package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.service.HeightMaterialService;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/height")
public class HeightMaterialController {
    @Autowired
    private HeightMaterialService heightMaterialService;
    //************************************
    // Get all frame height
    //************************************
    @GetMapping("/get")
    public ResponseEntity<ResponseCommon> getAllFrameHeight(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = heightMaterialService.getAllHeights();
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
    // Get all height material to insert
    //************************************
    @GetMapping("/getheightmaterial")
    public ResponseEntity<ResponseCommon> getAllFrameHeightMaterialToInset(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = heightMaterialService.getHeightsToInsertMaterial();
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
    // Get all height coversheet
    //************************************
    @GetMapping("/getheightcoversheet")
    public ResponseEntity<ResponseCommon> getAllFrameHeightCoverSheet(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = heightMaterialService.getHeightsToInsertCoverInsert();
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
    // Add frame height
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addFrameHeight(@RequestParam String frameHeight){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = heightMaterialService.addHeightMaterial(frameHeight);
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
    // Delete frame height by id
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteFrameHeight(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = heightMaterialService.deleteHeightMaterial(id);
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