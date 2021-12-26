package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.service.UnitMeasureService;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/unit")
public class UnitMeasureController {
    @Autowired
    private UnitMeasureService unitMeasureService;
    //************************************
    // Get all units
    //************************************
    @GetMapping("/get")
    public ResponseEntity<ResponseCommon> getAllUnits(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = unitMeasureService.getAllUnit();
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
    // Add units
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addUnit(@RequestParam String name){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = unitMeasureService.addUnitMeasure(name);
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
    // Delete unit
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteUnit(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = unitMeasureService.deleteUnitMeasure(id);
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
    //************************************
    // Get units material to insert
    //************************************
    @GetMapping("/getunitsmaterial")
    public ResponseEntity<ResponseCommon> getAllUnitsMaterialToInset(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = unitMeasureService.getUnitsToInsertMaterial();
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
    // Get all units coversheet to insert
    //************************************
    @GetMapping("/getunitcoversheet")
    public ResponseEntity<ResponseCommon> getUnitCoverSheetToCoverInsert(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = unitMeasureService.getUnitsToInsertCoverInsert();
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
}