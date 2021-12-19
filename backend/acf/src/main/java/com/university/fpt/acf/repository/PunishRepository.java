package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.BonusPenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PunishRepository extends JpaRepository<BonusPenalty,Long> {
    @Query("select  b from BonusPenalty b where b.id=:id and b.bonus=false ")
    BonusPenalty getPunishById(@Param("id") Long id);

    @Query("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status,b.effectiveDate,e.id,e.fullName) from BonusPenalty  b  inner  join  b.employees e where b.deleted=false and b.bonus = false ")
    String a();

    @Query(" select  COUNT(b.id) from Account a inner  join a.employee e inner  join  e.bonusPenalties b  " +
            " where a.username = :username and  b.effectiveDate between :dateStart and :dateEnd and b.status = false ")
    Integer getPunishInMonth(@Param("username") String username, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
}
