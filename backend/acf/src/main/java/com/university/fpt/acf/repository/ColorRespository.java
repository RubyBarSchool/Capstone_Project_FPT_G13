package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Color;
import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.vo.ColorVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRespository extends JpaRepository<Color,Long> {
    @Query("SELECT c.name FROM Company c where c.deleted=false AND c.id=:id")
    String checkExitCompany(@Param("id") Long id);
    @Query("SELECT c FROM Color c  where c.deleted = false and c.id=:id")
    Color getColorById(@Param("id") Long id);
}
