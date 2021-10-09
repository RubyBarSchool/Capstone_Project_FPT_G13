package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Role,Long> {

}
