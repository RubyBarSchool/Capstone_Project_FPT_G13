package com.university.fpt.acf.config.security.service;

import com.university.fpt.acf.config.security.entity.JwtResponse;
import com.university.fpt.acf.config.security.entity.ObjectLogin;

public interface AuthenticationService {
    // Generate jwt by login information
    JwtResponse generateJwtResponse(ObjectLogin objectLogin);
}
