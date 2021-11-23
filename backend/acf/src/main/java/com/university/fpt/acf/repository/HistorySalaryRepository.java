package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.HistorySalary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorySalaryRepository extends JpaRepository<HistorySalary,Long> {
}
