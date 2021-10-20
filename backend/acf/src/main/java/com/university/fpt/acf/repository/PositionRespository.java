package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRespository extends JpaRepository<Position,Long> {

}
