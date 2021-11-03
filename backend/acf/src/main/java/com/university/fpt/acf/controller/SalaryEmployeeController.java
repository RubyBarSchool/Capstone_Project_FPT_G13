package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
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
@RequestMapping(path = "/employee/salary")
public class SalaryEmployeeController {

    @Autowired
    private BonusService bonusService;

    // get salary with account

    // get bonus
    @PostMapping("/bonus")
    public ResponseEntity<ResponseCommon> searchColor() {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            list = bonusService.searchBonusUser();
            total = bonusService.totalSearchBonusUser();
            responseCommon.setData(list);
            message = "Get bonus successfully";
            if (total == 0) {
                message = "Get bonus not found";
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
    // get
}
