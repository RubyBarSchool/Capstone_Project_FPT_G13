package com.university.fpt.acf.controller;

import com.university.fpt.acf.config.security.entity.ObjectLogin;
import com.university.fpt.acf.config.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;
    //************************************
    // Login
    //************************************
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody ObjectLogin loginRequest) {
        return ResponseEntity.ok(authenticationService.generateJwtResponse(loginRequest));
    }

}
