package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AcceptPersonalLeaveApplicationAdminForm;
import com.university.fpt.acf.form.SearchPersonalLeaveAdminApplicationForm;
import com.university.fpt.acf.service.PersonalLeaveApplicationAdminService;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/acceptPersonalLeaveApplication")
public class PersonalLeaveApplicationAdminController {
    @Autowired
    PersonalLeaveApplicationAdminService personalLeaveApplicationService;
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchPersonalLeaveApplication(@RequestBody SearchPersonalLeaveAdminApplicationForm personalLeaveApplicationForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<SearchPersonalLeaveApplicationAdminVO> getAllPositionVOList = new ArrayList<>();
        try {
            getAllPositionVOList = personalLeaveApplicationService.searchPersonalApplication(personalLeaveApplicationForm);
            total = personalLeaveApplicationService.totalPersonalApplication(personalLeaveApplicationForm);
            responseCommon.setData(getAllPositionVOList);
            message = "Thành công!";
            if(total==0){
                message = "Không tìm thấy";
            }
            responseCommon.setTotal(total);
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = e.getMessage();
            responseCommon.setData(getAllPositionVOList);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }
    @PostMapping("/accept")
    public  ResponseEntity<ResponseCommon> acceptPersonalApplication(@RequestBody AcceptPersonalLeaveApplicationAdminForm acceptForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAccept = false;
        try {
            if(acceptForm.getIdApplication()==null ){
                message="Dữ liệu NULL! ";
            }else {
                checkAccept =personalLeaveApplicationService.acceptPersonalLeaveApplication(acceptForm);
                if(checkAccept==false){
                    message="Duyệt đơn lỗi!";
                }else{
                    message="Duyệt đơn thành công!";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAccept);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAccept);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    @PostMapping("/reject")
    public  ResponseEntity<ResponseCommon> rejectPersonalApplication(@RequestBody AcceptPersonalLeaveApplicationAdminForm acceptForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAccept = false;
        try {
            if(acceptForm.getIdApplication()==null ){
                message="Dữ liệu NULL! ";
            }else {
                checkAccept =personalLeaveApplicationService.acceptPersonalLeaveApplication(acceptForm);
                if(checkAccept==false){
                    message="Loại bỏ đơn lỗi!";
                }else{
                    message="Loại bỏ đơn thành công!";
                }
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkAccept);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkAccept);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
//    @GetMapping("/detail")
//    public ResponseEntity<ResponseCommon> getDetailPersonalLeaveApplicationAdmin(@RequestParam Long id){
//        ResponseCommon responseCommon = new ResponseCommon();
//        String message="";
//        SearchPersonalLeaveApplicationAdminVO data = new SearchPersonalLeaveApplicationAdminVO();
//        try {
//            data = personalLeaveApplicationService.detailPersonalApplicationById(id);
//            if(data==null){
//                message="Không tìm thấy đơn nghỉ phép";
//            }else{
//                message="Lấy đơn nghỉ phép thành công!";
//            }
//            responseCommon.setMessage(message);
//            responseCommon.setData(data);
//            responseCommon.setStatus(HttpStatus.OK.value());
//            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
//        }catch (Exception e){
//            message = e.getMessage();
//            responseCommon.setData(data);
//            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
//            responseCommon.setMessage(message);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
//        }
//    }

}
