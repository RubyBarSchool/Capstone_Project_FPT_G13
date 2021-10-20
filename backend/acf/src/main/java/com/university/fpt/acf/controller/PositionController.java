package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.form.RolesForm;
import com.university.fpt.acf.service.PositionService;
import com.university.fpt.acf.vo.GetAllRoleVO;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/position")
public class PositionController {
    @Autowired
    private PositionService positionService;
    @PostMapping
    public ResponseEntity<ResponseCommon> searchPositions(@RequestBody PositionForm positionForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<PositionResponseVO> getAllPositionVOList = new ArrayList<>();
        try {
            getAllPositionVOList = positionService.searchPosition(positionForm);
            responseCommon.setData(getAllPositionVOList);
            message = "Get position successfully";
            if(getAllPositionVOList.isEmpty()){
                message = "Get position not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get position !";
            responseCommon.setData(getAllPositionVOList);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
}
