package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.vo.DetailAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdvanceSalaryEmployeeRepository extends JpaRepository<AdvaceSalary,Long> {
    @Query("select a from AdvaceSalary a where a.deleted=false and a.id=:id")
    AdvaceSalary getAdvanceSalaryById(@Param("id") Long id);
    @Query("select new com.university.fpt.acf.vo.DetailAdvanceSalaryEmployeeVO(a.title,a.advaceSalary,a.comment,a.content,a.modified_by) from AdvaceSalary a where a.id=:id")
    DetailAdvanceSalaryEmployeeVO getDetailAdvanceSalaryEmployeeByIdAplication(@Param("id") Long id);
}
