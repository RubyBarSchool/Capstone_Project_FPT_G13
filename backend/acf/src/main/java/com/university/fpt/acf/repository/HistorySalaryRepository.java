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


    @Query("select new com.university.fpt.acf.vo.SearchSalaryVO(hs.id,hs.created_date,e.fullName,p.name,hs.countWork,hs.salary,hs.bonus,hs.penalty,hs.advanceSalary,hs.totalMoney,hs.status,hs.accountAccept,hs.dateAccept)  from Account  a inner  join  a.employee e inner  join e.historySalaries hs inner join e.position p where  hs.deleted = false ")
    HistorySalary getSalaryByEmployee1(@Param("idEmployee") Long idEmployee, @Param("dateCreated") LocalDate dateCreated);
}
