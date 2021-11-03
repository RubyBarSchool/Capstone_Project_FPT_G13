package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Repository
public interface BonusRepository extends JpaRepository<BonusPenalty,Long> {
    @Query("select  b from BonusPenalty b where b.id=:id and b.bonus = true")
    BonusPenalty getBonusById(@Param("id") Long id);

}
