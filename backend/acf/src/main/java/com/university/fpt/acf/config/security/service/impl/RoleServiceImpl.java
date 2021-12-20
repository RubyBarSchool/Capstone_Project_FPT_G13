package com.university.fpt.acf.config.security.service.impl;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.config.security.repository.RoleRepository;
import com.university.fpt.acf.config.security.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role saveRole(Role role) {
        //************************************
        // save role
        //************************************
        log.info("Saving new role to database");
        return roleRepository.save(role);
    }
}
