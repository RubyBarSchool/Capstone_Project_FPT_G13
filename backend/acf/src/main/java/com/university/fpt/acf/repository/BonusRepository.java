package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BonusRepository extends JpaRepository<BonusPenalty,Long> {
    @Query("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status,b.effectiveDate) from BonusPenalty b where b.deleted=false ")
    SearchBonusAdminVO get();
}
