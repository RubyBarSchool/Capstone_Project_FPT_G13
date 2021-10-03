package com.university.fpt.acf.controller;

import com.university.fpt.acf.common.entity.ResponseCommon;
import com.university.fpt.acf.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/spadmin/role")
public class RoleController {
    @Autowired
    private RolesService rolesService;
    @GetMapping
    public ResponseEntity<ResponseCommon> getAllRoles(){
        ResponseCommon responseCommon = new ResponseCommon();
        responseCommon.setData(rolesService.getAllRole());
        responseCommon.setStatus(HttpStatus.OK.value());
        return new ResponseEntity<>(responseCommon,HttpStatus.OK);
    }

}
