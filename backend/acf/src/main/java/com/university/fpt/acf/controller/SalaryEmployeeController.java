package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAndPunishForm;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.service.PunishService;
import com.university.fpt.acf.service.SalaryService;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAndPunishVO;
import com.university.fpt.acf.vo.SearchSalaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/employee/salary")
public class SalaryEmployeeController {

    @Autowired
    private BonusService bonusService;

    @Autowired
    private PunishService punishService;

    @Autowired
    private SalaryService salaryService;
    //************************************
    // Get salary with account
    //************************************

    @PostMapping("")
    public ResponseEntity<ResponseCommon> salary(@Valid @RequestBody BonusPunishForm bonusPunishForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        Integer total = 0;
        try {
            searchSalaryVOS = salaryService.searchSalary(bonusPunishForm);
            total = salaryService.getTotalAllSalary(bonusPunishForm);
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setTotal(total);
            message = "Thành công ";
            if (total.intValue() == 0) {
                message = "Không tìm thấy";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(searchSalaryVOS);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    //************************************
    // Get bonus  with combination of fields: date, checkNow
    //************************************
    @PostMapping("/bonus")
    public ResponseEntity<ResponseCommon> getBonus(@Valid @RequestBody BonusPunishForm bonusPunishForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            list = bonusService.searchBonusUser(bonusPunishForm);
            total = bonusService.totalSearchBonusUser(bonusPunishForm);
            responseCommon.setData(list);
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
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }
    //************************************
    // Get punish
    //************************************
    @PostMapping("/punish")
    public ResponseEntity<ResponseCommon> getPunish(@Valid @RequestBody BonusPunishForm bonusPunishForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            list = punishService.searchPunishUser(bonusPunishForm);
            total = punishService.totalSearchPunishUser(bonusPunishForm);
            responseCommon.setData(list);
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
            responseCommon.setData(list);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        }
    }

    //************************************
    // Get bonus and punish
    //************************************
    @PostMapping("/bonusandpunish")
    public ResponseEntity<ResponseCommon> getBonusAndPunish(@Valid @RequestBody SearchBonusAndPunishForm searchBonusAndPunishForm) {
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total = 0;
        List<SearchBonusAndPunishVO> list = new ArrayList<>();
        try {
            list = bonusService.searchBonusAndPunish(searchBonusAndPunishForm);
            total = bonusService.totalSearchBonusAndPunish(searchBonusAndPunishForm);
            responseCommon.setData(list);
            message = "Thành công";
            if (total == 0) {
                message = "Không thành công";
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
