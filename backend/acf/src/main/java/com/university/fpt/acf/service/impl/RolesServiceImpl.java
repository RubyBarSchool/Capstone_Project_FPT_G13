package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.repository.RolesRepository;
import com.university.fpt.acf.service.RolesService;
import com.university.fpt.acf.vo.GetAllRoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolesServiceImpl implements RolesService {
    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public List<GetAllRoleVO> getAllRole() {
        List<Role> roles =  rolesRepository.findAll();
        List<GetAllRoleVO> listRolesOutput = new ArrayList<>();
        for(Role i : roles){
            GetAllRoleVO roleVO = new GetAllRoleVO();
            roleVO.setIdRole(i.getId());
            roleVO.setCode(i.getCode());
            roleVO.setRoleName(i.getName());
            roleVO.setTime(i.getModified_date());
            listRolesOutput.add(roleVO);
        }
        return listRolesOutput;
    }
}
