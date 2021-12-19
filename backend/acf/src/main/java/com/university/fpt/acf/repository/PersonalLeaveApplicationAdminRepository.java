package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PersonaLeaveApplication;

import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface PersonalLeaveApplicationAdminRepository extends JpaRepository<PersonaLeaveApplication,Long> {
 @Query("SELECT p FROM PersonaLeaveApplication p where p.deleted=false and p.id=:id")
 PersonaLeaveApplication getApplicationById(@Param("id") Long id);
 @Query("select new com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO(p.id,p.created_date,p.dateStart,p.dateEnd,p.fileAttach,p.title,p.comment,p.content,e.id,e.fullName,p.accept,p.dateAccept) from PersonaLeaveApplication p inner join p.employee  e where p.deleted=false and p.id=:id")
 SearchPersonalLeaveApplicationAdminVO detailPersonLeaveApplication(@Param("id") Long id);
 @Query("SELECT new com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO(p.id,p.created_date,p.dateStart,p.dateEnd,p.fileAttach,p.title,p.comment,p.content,e.id,e.fullName,p.accept,p.dateAccept) From PersonaLeaveApplication p left join p.employee e where 1=1")
 SearchPersonalLeaveApplicationAdminVO getQ();

 @Query("SELECT COUNT(p.id) From PersonaLeaveApplication p where p.deleted = false and p.accept ='-1'")
 Integer getPersonaLeaveApplicationConfirm();
}
