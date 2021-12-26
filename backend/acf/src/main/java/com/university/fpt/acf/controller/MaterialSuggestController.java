package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.form.SearchAllEmployeeForm;
import com.university.fpt.acf.repository.MaterialRepository;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.MaterialSuggestVO;
import com.university.fpt.acf.vo.SearchEmployeeVO;
import com.university.fpt.acf.vo.SuggestMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/materialsuggest")
public class MaterialSuggestController {

    @Autowired
    private MaterialService materialService;

    //************************************
    // Search suggest material  with combination of fields: count, type
    //************************************
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchSuggestMaterial(@RequestBody MaterialSuggestFrom materialSuggestFrom){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total;
        List<SuggestMaterialVO> suggestMaterialVOS = new ArrayList<>();
        try {
            suggestMaterialVOS = materialService.searchSuggestMaterial(materialSuggestFrom);
            responseCommon.setData(suggestMaterialVOS);
            message = "tìm kiếm gợi ý thành công";
            if(suggestMaterialVOS.isEmpty()){
                message = "Không tìm thấy gợi ý vật liệu";
            }
            total = suggestMaterialVOS.size();
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Không thể tìm kiếm gợi ý vật liệu";
            responseCommon.setData(suggestMaterialVOS);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
}
