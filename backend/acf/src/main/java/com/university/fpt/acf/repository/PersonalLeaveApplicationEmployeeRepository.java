package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PersonaLeaveApplication;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalLeaveApplicationEmployeeRepository extends JpaRepository<PersonaLeaveApplication,Long> {
    @Query("select new com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO(p.id,p.created_date,p.dateAccept,p.dateStart,p.dateEnd,p.fileAttach,p.title,p.comment,p.content,p.accept) from PersonaLeaveApplication p where p.id=:id")
    SearchPersonalApplicationEmployeeVO detailPersonalLeaveAppEmployee(@Param("id") Long id);
}
