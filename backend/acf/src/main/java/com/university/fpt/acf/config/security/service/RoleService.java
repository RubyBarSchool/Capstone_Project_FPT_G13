package com.university.fpt.acf.config.security.service;

import com.university.fpt.acf.config.security.entity.Role;

public interface RoleService {
    // Add a new role
    Role saveRole(Role role);
}
