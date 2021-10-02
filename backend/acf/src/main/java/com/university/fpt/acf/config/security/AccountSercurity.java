package com.university.fpt.acf.config.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.stream.Collectors;

public class AccountSercurity {

    private Authentication authentication;
    private String userName;
    private List<String> roleList;

    public AccountSercurity(){
         this.authentication = SecurityContextHolder.getContext().getAuthentication();

    }

    public String getUserName() {
        if (!(this.authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = this.authentication.getName();
            return currentUserName;
        }
        return "";
    }

    public List<String> getRoleList() {
        return  this.authentication.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

    }
}
