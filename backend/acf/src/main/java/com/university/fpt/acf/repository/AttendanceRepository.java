package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.TimeKeep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<TimeKeep,Long> {
    @Query("select t.employee.id from TimeKeep t where t.date = :date")
    List<Long> getAllListID(@Param("date") LocalDate date);

    @Query("select t from TimeKeep t where t.id = :id")
    TimeKeep getTimeKeepByID(@Param("id") Long id);

    @Query("select t from Account a inner  join a.employee e inner  join e.timeKeeps t where a.username = :username " +
            " and a.deleted = false and e.deleted = false and t.deleted = false and t.date >= :dateStart and t.date < :dateEnd")
    List<TimeKeep> getTimeKeepInMonth(@Param("username") String username,@Param("dateStart") LocalDate dateStart,@Param("dateEnd") LocalDate dateEnd);
}
