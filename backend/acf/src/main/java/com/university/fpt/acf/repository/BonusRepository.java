package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public interface BonusRepository extends JpaRepository<BonusPenalty, Long> {
    @Query("select  b from BonusPenalty b where b.id=:id and b.bonus = true")
    BonusPenalty getBonusById(@Param("id") Long id);


    @Query("select  b from BonusPenalty b inner  join  b.employees e where e.id =:id and b.effectiveDate between :dateStart and :dateEnd and b.status = true")
    List<BonusPenalty> getBonusPenaltyOfEmployee(@Param("id") Long id, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Query(" select  COUNT(b.id) from Account a inner  join a.employee e inner  join  e.bonusPenalties b  " +
            " where a.username = :username and  b.effectiveDate between :dateStart and :dateEnd and b.status = true ")
    Integer getBonusInMonth(@Param("username") String username, @Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
}
