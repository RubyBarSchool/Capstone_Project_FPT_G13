package com.university.fpt.acf.service;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.vo.GetAllRoleVO;

import java.util.List;

public interface RolesService {
    List<GetAllRoleVO> getAllRole();
}
