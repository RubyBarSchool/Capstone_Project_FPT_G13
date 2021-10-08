package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByFullNameIsLike(String fullName, Pageable pageable);
    List<Employee> findEmployeeByFullNameIsLike(String fullName);
    @Query("select e.fullName,a.username from Employee e  left join Account a on e.id = a.employee.id  where a.username " +
            "is null order by e.fullName asc  ")
    List<String> getTop10EmployeeNotAccount(Pageable pageable);
}
