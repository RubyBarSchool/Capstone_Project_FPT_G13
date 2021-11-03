package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.BonusPenalty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PunishRepository extends JpaRepository<BonusPenalty,Long> {
    @Query("select  b from BonusPenalty b where b.id=:id and b.bonus=false ")
    BonusPenalty getPunishById(@Param("id") Long id);
}
