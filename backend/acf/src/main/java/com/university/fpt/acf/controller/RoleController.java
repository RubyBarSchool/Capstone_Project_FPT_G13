package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.form.GetAllAccountForm;
import com.university.fpt.acf.form.RolesForm;
import com.university.fpt.acf.service.RolesService;
import com.university.fpt.acf.vo.GetAllRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/spadmin/role")
public class RoleController {
    @Autowired
    private RolesService rolesService;
    @PostMapping
    public ResponseEntity<ResponseCommon> getAllRoles(@RequestBody RolesForm rolesForm){
        ResponseCommon responseCommon = new ResponseCommon();
        String message = "";
        List<GetAllRoleVO> getAllRoleVOList = new ArrayList<>();
        try {
            getAllRoleVOList = rolesService.getRoles(rolesForm);
            responseCommon.setData(getAllRoleVOList);
            message = "Get roles successfully";
            if(getAllRoleVOList.isEmpty()){
                message = "Get roles not found";
            }
            responseCommon.setStatus(HttpStatus.OK.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.OK).body(responseCommon);
        } catch (Exception e) {
            message = "Could not get roles !";
            responseCommon.setData(getAllRoleVOList);
            responseCommon.setStatus(HttpStatus.BAD_REQUEST.value());
            responseCommon.setMessage(message);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(responseCommon);
        }
    }

}
