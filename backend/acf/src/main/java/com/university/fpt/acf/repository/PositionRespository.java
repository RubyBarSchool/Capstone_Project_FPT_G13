package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRespository extends JpaRepository<Position,Long> {
    @Query("SELECT p.name FROM Position p where p.deleted = false and LOWER(p.name) =:name")
    String checkExitPosition(@Param("name") String name);
    @Query("SELECT p.name FROM Position p where p.deleted = false and p.id=:id")
    String CheckExitPositionById(@Param("id") Long id);
    @Query("SELECT p FROM Position p where p.deleted = false and p.id=:id")
    Position getPositionById(@Param("id") Long id);
    @Query("SELECT p.name FROM Position p where p.deleted = true and p.id=:id")
    String checkDeletePositionById(@Param("id") Long id);
    @Query("SELECT COUNT(e) FROM Employee e where e.position.id = :id")
    Long checkPositionInEmployeeToDeletePosition(@Param("id") Long id);

}
