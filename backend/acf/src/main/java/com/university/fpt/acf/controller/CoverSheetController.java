package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddMaterialForm;
import com.university.fpt.acf.form.AddUnitFrameHeightForm;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.form.UpdateMaterialForm;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/coversheet")
public class CoverSheetController {
    @Autowired
    private MaterialService materialService;
    //************************************
    // Search all cover sheet with combination of fields: codeMaterial,frame,group, unit, company
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchCoverSheet(@RequestBody SearchMaterialForm searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<MaterialVO> list = new ArrayList<>();
        try {
            list = materialService.searchCoverSheet(searchForm);
            responseCommon.setData(list);
            total= materialService.totalSearchCoverSheet(searchForm);
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Get all cover sheet
    //************************************
    @GetMapping("/getcoversheets")
    public ResponseEntity<ResponseCommon> getCoverSheets(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialService.getAllCoverSheet();
            responseCommon.setData(list);
            total= list.size();
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Get all unit by id cover sheet
    //************************************
    @PostMapping("/getunitbycoversheet")
    public ResponseEntity<ResponseCommon> getUnitByCoverSheet(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<UnitMeasureVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getUnitsByCoverSheet(id);
            responseCommon.setData(listUnits);
            total = listUnits.size();
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
            responseCommon.setData(listUnits);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Get all cover sheet by id unit
    //************************************
    @PostMapping("/getcoversheetbyunit")
    public ResponseEntity<ResponseCommon> searchCoverSheetByUnit(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GetAllMaterialVO> listCoverSheet = new ArrayList<>();
        try {
            listCoverSheet = materialService.getCoverSheetByUnit(id);
            responseCommon.setData(listCoverSheet);
            total = listCoverSheet.size();
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
            responseCommon.setData(listCoverSheet);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Get all height with combination of fields: coversheet, frame
    //************************************
    @PostMapping("/getheightbycoversheetandframe")
    public ResponseEntity<ResponseCommon> getHeightsByCoverSheetAndFrame(@RequestBody Add2MaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<HeightMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getHeightByCoverSheetAndFrame(addForm.getId1(),addForm.getId2());
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công";
            if (total == 0) {
                message = "Đã tồn tại tất cả chiều cao với mã tấm phủ"+addForm.getName1()+" và khung "+addForm.getName2();
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
    //************************************
    // Get all cover sheet with combination of fields: frame, height
    //************************************
    @PostMapping("/getcoversheetbyframeandheight")
    public ResponseEntity<ResponseCommon> getCoverSheetByFrameAndHeight(@RequestBody Add2MaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<GetAllMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getCoverSheetByHeightFrame(addForm.getId2(),addForm.getId1());
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công";
            if (total == 0) {
                message = "Đã tồn tại tất cả mã tấm phủ với khung "+addForm.getName2()+" và chiều cao "+addForm.getName1();
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
    //************************************
    // Get all frame with combination of fields: coversheet, height
    //************************************
    @PostMapping("/getframebycoversheetandheight")
    public ResponseEntity<ResponseCommon> getFrameByCoverSheetAndHeight(@RequestBody Add2MaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<FrameMaterialVO> listUnits = new ArrayList<>();
        try {
            listUnits = materialService.getFrameByCoverSheetAndHeight(addForm.getId1(), addForm.getId2());
            responseCommon.setData(listUnits);
            total = listUnits.size();
            message = "Thành công";
            if (total == 0) {
                message = "Đã tổn tại tất cả khung với mã tấm phủ "+addForm.getName1()+" chiều cao "+addForm.getName2();
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
    //************************************
    // Add cover sheet
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addCoverSheet(@RequestBody AddMaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addCoverSheet(addForm);
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Update coversheet
    //************************************
    @PutMapping("/update")
    public ResponseEntity<ResponseCommon> updateCoverSheet(@RequestBody UpdateMaterialForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.updateCoverSheet(updateForm);
            if(checkAdd==true){
                message="Chỉnh sửa thành công";
            }else{
                message="Chỉnh sửa không thành công";
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
    // Delete coversheet
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteCoverSheet(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.deleteCoverSheet(id);
            if(checkAdd==true){
                message="Xóa thành công";
            }else{
                message="Xóa không thành công";
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
    // Add unit for coversheet
    //************************************
    @PostMapping("/addunit")
    public ResponseEntity<ResponseCommon> addUnitCoverSheet(@RequestBody AddUnitFrameHeightForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addUnitInCoverSheet(addForm);
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    //************************************
    // Add frameheight for coversheet
    //************************************
    @PostMapping("/addframeheight")
    public ResponseEntity<ResponseCommon> addFrameHeightCoverSheet(@RequestBody AddUnitFrameHeightForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addFrameHeightCoverSheet(addForm);
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
}

