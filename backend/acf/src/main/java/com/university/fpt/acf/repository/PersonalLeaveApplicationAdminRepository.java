package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PersonaLeaveApplication;

import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonalLeaveApplicationAdminRepository extends JpaRepository<PersonaLeaveApplication,Long> {
 @Query("SELECT p FROM PersonaLeaveApplication p where p.deleted=false and p.id=:id")
 PersonaLeaveApplication getApplicationById(@Param("id") Long id);
 @Query("select new com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO(p.id,p.fileAttach,p.title,p.comment,p.content,e.id,e.fullName,p.accept) from PersonaLeaveApplication p inner join p.employee  e where p.deleted=false and p.id=:id")
 SearchPersonalLeaveApplicationAdminVO detailPersonLeaveApplication(@Param("id") Long id);

}
