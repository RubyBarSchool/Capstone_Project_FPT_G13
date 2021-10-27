package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRespository extends JpaRepository<Company,Long> {
    @Query("SELECT c.name FROM Company c where c.deleted=false AND c.name=:name")
    String checkExitCompanyByName(@Param("name") String  name);
    @Query("SELECT c FROM Company c  where c.deleted = false and c.id=:id")
    Company getCompanyById(@Param("id") Long id);
    @Query("SELECT count(c.id) FROM Color c  where c.deleted = false and c.company.id=:id")
    Long checkExitCompanyInColor(@Param("id") Long id);
}
