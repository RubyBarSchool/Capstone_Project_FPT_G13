package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.entity.Role;
import com.university.fpt.acf.repository.RoleRepository;
import com.university.fpt.acf.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        log.info("Saving new role to database");
        return roleRepository.save(role);
    }
}
