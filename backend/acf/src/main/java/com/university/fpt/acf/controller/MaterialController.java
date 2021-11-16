package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchMaterial(@RequestBody SearchMaterialForm searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<MaterialVO> list = new ArrayList<>();
        try {
            list = materialService.searchMaterial(searchForm);
            responseCommon.setData(list);
            total= materialService.totalSearchMaterial(searchForm);
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
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/getunitbymaterial")
    public ResponseEntity<ResponseCommon> getUnitByMaterial(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<UnitMeasureVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getUnitsByMaterial(id);
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công!";
            if (total == 0) {
                message = "Không tìm thấy!";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listUnits);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/getmaterialbyunit")
    public ResponseEntity<ResponseCommon> searchMaterialByUnit(@RequestParam Long unit){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GetAllMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getMaterialByUnit(unit);
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công!";
            if (total == 0) {
                message = "Không tìm thấy!";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listUnits);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/getheightbymaterialandframe")
    public ResponseEntity<ResponseCommon> getHeightsByMaterialAndFrame(@RequestBody Add2MaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<HeightMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getHeightSByMaterialAndFrame(addForm.getId1(),addForm.getId2());
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công!";
            if (total == 0) {
                message = "Đã tồn tại tất cả chiều cao với mã vật liệu "+addForm.getName1()+" và khung "+addForm.getName2();
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listUnits);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }

    @PostMapping("/getMaterialbyframeandheight")
    public ResponseEntity<ResponseCommon> getMaterialByFrameAndHeight(@RequestBody Add2MaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GetAllMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getMaterialByHeightFrame(addForm.getId2(), addForm.getId1());
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công!";
            if (total == 0) {
                message = "Đã tồn tại tất cả mã vật liệu với khung "+addForm.getName2()+" và chiều cao "+addForm.getName1();
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listUnits);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }

    @PostMapping("/getframebymaterialandheight")
    public ResponseEntity<ResponseCommon> getFrameByMaterialAndHeight(@RequestBody Add2MaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<FrameMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getFrameByMaterialAndHeight(addForm.getId1(),addForm.getId2());
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công!";
            if (total == 0) {
                message = "Đã tổn tại tất cả khung với mã vật liệu "+addForm.getName1()+" chiều cao "+addForm.getName2();
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(listUnits);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    
    @GetMapping("/getmaterials")
    public ResponseEntity<ResponseCommon> getMaterials(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialService.getAllMaterial();
            responseCommon.setData(list);
            total= list.size();
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
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addMaterial(@RequestBody AddMaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addMaterial(addForm);
            if(checkAdd==true){
                message="Thêm thành công!";
            }else{
                message="Thêm không thành công!";
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
    public ResponseEntity<ResponseCommon> updateMaterial(@RequestBody UpdateMaterialForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.updateMaterial(updateForm);
            if(checkAdd==true){
                message="Chỉnh sửa thành công!";
            }else{
                message="Chỉnh sửa không thành công!";
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
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteMaterial(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.deleteMaterial(id);
            if(checkAdd==true){
                message="Xóa thành công!";
            }else{
                message="Xóa không thành công!";
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
    @PostMapping("/addunit")
    public ResponseEntity<ResponseCommon> addUnitMaterial(@RequestBody AddUnitFrameHeightForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addUnitInMaterial(addForm);
            if(checkAdd==true){
                message="Thêm thành công!";
            }else{
                message="Thêm không thành công!";
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
    @PostMapping("/addframeheight")
    public ResponseEntity<ResponseCommon> addFrameHeightMaterial(@RequestBody AddUnitFrameHeightForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addFrameHeightMaterial(addForm);
            if(checkAdd==true){
                message="Thêm thành công!";
            }else{
                message="Thêm không thành công!";
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
}
