package com.university.fpt.acf.config.security.repository;

import com.university.fpt.acf.config.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByCode(String code);
    Role findByName(String code);
}
