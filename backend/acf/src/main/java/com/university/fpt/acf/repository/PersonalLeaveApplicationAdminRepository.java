package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PersonaLeaveApplication;
import com.university.fpt.acf.vo.SearchEmployeeVO;
import org.apache.catalina.LifecycleState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PersonalLeaveApplicationAdminRepository extends JpaRepository<PersonaLeaveApplication,Long> {
 @Query("SELECT p FROM PersonaLeaveApplication p where p.deleted=false and p.id=:id")
    PersonaLeaveApplication getApplicationById(@Param("id") Long id);
}
