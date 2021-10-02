package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByContent(String content, Pageable pageable);
}
