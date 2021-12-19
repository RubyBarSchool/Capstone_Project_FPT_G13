package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.vo.DetailAdvanceSalaryAdminVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvanceSalaryAdminRepository extends JpaRepository<AdvaceSalary,Long> {
    @Query("select new com.university.fpt.acf.vo.DetailAdvanceSalaryAdminVO(a.id,a.created_date,a.title,a.advaceSalary,a.accept,e.id,e.fullName,a.content) from AdvaceSalary a inner join a.employee  e where a.deleted=false and a.id=:id")
    DetailAdvanceSalaryAdminVO getDetailById(@Param("id") Long id);
    @Query("select a from AdvaceSalary a inner join a.employee  e where a.deleted=false and a.id=:id")
    AdvaceSalary getDetailAdvanceSalaryById(@Param("id") Long id);

    @Query("select COUNT(a.id) from AdvaceSalary a where a.deleted = false and a.accept = '-1'")
    Integer getAdvanceSalaryConfirm();
}

