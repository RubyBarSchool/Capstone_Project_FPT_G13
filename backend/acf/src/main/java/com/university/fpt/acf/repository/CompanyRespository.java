package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRespository extends JpaRepository<Company,Long> {
    @Query("SELECT c.id FROM Company c where c.deleted=false AND c.name=:name")
    Long checkExitCompanyById(@Param("name") String  name);
    @Query("SELECT c FROM Company c  where c.deleted = false and c.id=:id")
    Company getCompanyById(@Param("id") Long id);
    @Query("SELECT c.name FROM Company c where c.deleted=false AND c.name=:name")
    String checkExitCompanyByName(@Param("name") String  name);
    @Query("SELECT c.phone FROM Company c where c.deleted=false AND c.phone=:phone")
    String checkExitPhoneCompanyByPhone(@Param("phone") String  phone);
    @Query("SELECT c.id FROM Company c where c.deleted=false AND c.phone=:phone")
    Long checkExitPhoneCompanyById(@Param("phone") String  phone);
    @Query("SELECT c.email FROM Company c where c.deleted=false AND c.email=:email")
    String checkExitEmailCompanyByEmail(@Param("email") String  email);
    @Query("SELECT c.id FROM Company c where c.deleted=false AND c.email=:email")
    Long checkExitEmailCompanyById(@Param("email") String  email);
}
