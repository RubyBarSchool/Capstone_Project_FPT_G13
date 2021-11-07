package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.AddAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.UpdateAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.service.AdvanceSalaryEmployeeService;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/employee/advanceSalary")
public class AdvanceSalaryEmployeeController {
    @Autowired
    private AdvanceSalaryEmployeeService advanceSalaryEmployeeService;
    @PostMapping("/search")
    public ResponseEntity<ResponseCommon> searchAdvanceSalaryEmployee(@RequestBody SearchAdvanceEmployeeForm searchForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        int total=0;
        List<GetAllAdvanceSalaryEmployeeVO> list = new ArrayList<>();
        try {

            list = advanceSalaryEmployeeService.searchAdvanceSalaryEmployee(searchForm);
            total = advanceSalaryEmployeeService.totalSearch(searchForm);
            responseCommon.setData(list);
            message = "Tìm kiếm ứng lương thành công";
            if(total==0){
                message = "không tim thấy đơn ứng lương của bạn";
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
//    @GetMapping("/detail")
//    public ResponseEntity<ResponseCommon> getDetailAdvanceSalaryEmployee(@RequestParam Long id){
//        ResponseCommon responseCommon = new ResponseCommon();
//        String message="";
//        DetailAdvanceSalaryEmployeeVO data = new DetailAdvanceSalaryEmployeeVO();
//        try {
//            data = advanceSalaryEmployeeService.getDetailAdvanceSalaryEmployee(id);
//            if(data==null){
//                message="Không tìm thấy đơn ứng lương";
//            }else{
//                message="Lấy đơn ứng lương  thành công!";
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

    @PostMapping("/add")
    public ResponseEntity<ResponseCommon> addAdvanceSalary(@RequestBody AddAdvanceSalaryEmployeeForm addForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkAdd = false;
        try {

            if(addForm==null){
                message="Thông tin đang trống!";
            }
            checkAdd = advanceSalaryEmployeeService.addAdvanceSalaryEmployee(addForm);
            if(checkAdd==true){
                message="Thêm đơn thành công!";
            }else{
                message="Thêm đơn không thành công!";
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
    public ResponseEntity<ResponseCommon> updateAdvanceSalary(@RequestBody UpdateAdvanceSalaryEmployeeForm updateForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkUpdate = false;
        try {

            if(updateForm==null){
                message="Thông tin đang trống!";
            }
            checkUpdate = advanceSalaryEmployeeService.updateAdvanceSalaryEmployee(updateForm);
            if(checkUpdate==true){
                message="Chỉnh sửa đơn thành công!";
            }else{
                message="Chỉnh sửa đơn không thành công!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkUpdate);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseCommon> deleteAdvanceSalary(@RequestParam Long id){
        ResponseCommon responseCommon = new ResponseCommon();
        String message="";
        Boolean checkDelete = false;
        try {
            checkDelete = advanceSalaryEmployeeService.deleteAdvanceSalaryEmployee(id);
            if(checkDelete==true){
                message="Xóa đơn thành công!";
            }else{
                message="Xóa đơn không thành công!";
            }
            responseCommon.setMessage(message);
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.OK.value());
            return new ResponseEntity<>(responseCommon,HttpStatus.OK);
        }catch (Exception e){
            message = e.getMessage();
            responseCommon.setData(checkDelete);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseCommon);
        }
    }

}
