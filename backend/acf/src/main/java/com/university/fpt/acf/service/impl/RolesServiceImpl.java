package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.File;
import com.university.fpt.acf.form.RolesForm;
import com.university.fpt.acf.repository.RolesCustomRepository;
import com.university.fpt.acf.repository.RolesRepository;
import com.university.fpt.acf.service.RolesService;
import com.university.fpt.acf.vo.GetAllRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private RolesCustomRepository rolesCustomRepository;

    //************************************
    // Get all roles
    //************************************
    @Override
    public List<GetAllRoleVO> getRoles(RolesForm rolesForm) {
        List<GetAllRoleVO> getAllRoleVOS = new ArrayList<>();
        try {
            getAllRoleVOS = rolesCustomRepository.getRoles(rolesForm);
        } catch (Exception e) {
            throw new RuntimeException("Error role repository " + e.getMessage());
        }
        return getAllRoleVOS;
    }
    //************************************
    // Get total all roles
    //************************************
    @Override
    public int totalGetAllRole(RolesForm rolesForm) {
        int size = 0;
        try {
            size = rolesCustomRepository.totalGetRoles(rolesForm);
        } catch (Exception e) {
            throw new RuntimeException("Error role repository " + e.getMessage());
        }
        return size;
    }
}
