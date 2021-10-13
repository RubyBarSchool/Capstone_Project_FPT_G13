package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.vo.GetAllAccountVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    List<Employee> findByFullNameIsLike(String fullName, Pageable pageable);

    @Query("select e.fullName from Employee e " +
            " where e.id = :id and e.deleted = false ")
    String getFullNameById(@Param("id") Long id);

    @Query("select new com.university.fpt.acf.vo.GetAllEmployeeVO(e.id,e.fullName) from Employee e  left join Account a on e.id = a.employee.id  " +
            "where a.username is null and LOWER(e.fullName) LIKE :fullname order by e.fullName asc  ")
    List<GetAllEmployeeVO> getTop10EmployeeNotAccount(@Param("fullname") String fullName,Pageable pageable);
}
