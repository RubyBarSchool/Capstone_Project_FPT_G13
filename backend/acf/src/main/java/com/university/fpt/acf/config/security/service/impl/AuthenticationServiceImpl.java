package com.university.fpt.acf.config.security.service.impl;

import com.university.fpt.acf.config.security.entity.JwtResponse;
import com.university.fpt.acf.config.security.JwtUtils;
import com.university.fpt.acf.config.security.entity.ObjectLogin;
import com.university.fpt.acf.config.security.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public JwtResponse generateJwtResponse(ObjectLogin objectLogin) throws AuthenticationException {
        //************************************
        // Pass the login information to the Authentication spring security check if it is satisfied, it will continue otherwise it will give an error
        // set Authentication to sercurityContextHolder
        // Call the function that generates the token and returns the client
        //************************************
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(objectLogin.getUsername(), objectLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getUsername(),
                roles);
    }
}
