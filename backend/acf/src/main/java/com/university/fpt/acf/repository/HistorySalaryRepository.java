package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.HistorySalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface HistorySalaryRepository extends JpaRepository<HistorySalary,Long> {

    @Query("select hs from HistorySalary hs where hs.created_date = :dateCreated and hs.employee.id = :idEmployee")
    HistorySalary getSalaryByEmployee(@Param("idEmployee") Long idEmployee, @Param("dateCreated") LocalDate dateCreated);
}
