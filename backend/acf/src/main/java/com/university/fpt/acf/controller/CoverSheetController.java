package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddMaterialForm;
import com.university.fpt.acf.form.AddUnitFrameHeightForm;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.form.UpdateMaterialForm;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.MaterialVO;
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
    public ResponseEntity<ResponseCommon> addCoverSheet(@RequestBody AddMaterialForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addCoverSheet(addForm);
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
    public ResponseEntity<ResponseCommon> updateCoverSheet(@RequestBody UpdateMaterialForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.updateCoverSheet(updateForm);
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
    public ResponseEntity<ResponseCommon> deleteCoverSheet(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.deleteCoverSheet(id);
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
    public ResponseEntity<ResponseCommon> addUnitCoverSheet(@RequestBody AddUnitFrameHeightForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addUnitInCoverSheet(addForm);
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
    public ResponseEntity<ResponseCommon> addFrameHeightCoverSheet(@RequestBody AddUnitFrameHeightForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {
            checkAdd = materialService.addUnitInCoverSheet(addForm);
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

