package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddFrameMaterialForm;
import com.university.fpt.acf.form.SearchAllFrame;
import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.service.FrameMaterialService;
import com.university.fpt.acf.vo.FrameMaterialVO;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/framematerial")
public class FrameMaterialController {
    @Autowired
    private FrameMaterialService  frameService;
    //************************************
    // Add frame material
    //************************************
    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addFrameMaterial(@RequestBody AddFrameMaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = frameService.addFrame(addForm);
            if(checkAdd==true){
                message="Thêm thành công";
            }else{
                message="Khung đã tồn tại";
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
    // Search all frame material with combination of fields: length, width
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchFrame(@RequestBody SearchFrameMaterialForm searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<SearchFrameMaterialVO> list = new ArrayList<>();
        try {
            list = frameService.searchFrame(searchForm);
            responseCommon.setData(list);
            total=frameService.totalSearch(searchForm);
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
    // Search all frame by frameName
    //************************************
    @PostMapping("/getframe")
    public ResponseEntity<ResponseCommon> getAllFrame(@RequestBody SearchAllFrame searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<FrameMaterialVO> list = new ArrayList<>();
        try {
            list = frameService.searchAllFrame(searchForm);
            responseCommon.setData(list);
            total=frameService.totalsearchAllFrame(searchForm);
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
    // Delete frame material by id
    //************************************
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteFrameMaterial(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = frameService.deleteFrame(id);
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
    // Get all frame coversheet
    //************************************
    @GetMapping("/getframecoversheet")
    public ResponseEntity<ResponseCommon> getAllFrameCoverSheet(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<FrameMaterialVO> list = new ArrayList<>();
        try {
            list = frameService.getFrameCoverSheetToInsert();
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    //************************************
    // Search all frame material
    //************************************
    @GetMapping("/getframematerial")
    public ResponseEntity<ResponseCommon> getAllFrameMaterial(){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<FrameMaterialVO> list = new ArrayList<>();
        try {
            list = frameService.getFrameMaterialToInsert();
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
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
}
