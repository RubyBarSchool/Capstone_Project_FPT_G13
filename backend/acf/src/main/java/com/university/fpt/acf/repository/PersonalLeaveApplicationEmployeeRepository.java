package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PersonaLeaveApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalLeaveApplicationEmployeeRepository extends JpaRepository<PersonaLeaveApplication,Long> {
}
