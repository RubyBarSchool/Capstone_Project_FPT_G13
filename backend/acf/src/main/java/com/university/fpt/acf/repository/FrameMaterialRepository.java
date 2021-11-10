package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.FrameMaterial;
import com.university.fpt.acf.vo.HeightMaterialVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrameMaterialRepository extends JpaRepository<FrameMaterial,Long> {
    @Query("select f from FrameMaterial f where f.id =:id ")
    FrameMaterial getFrameById(@Param("id") Long id);
}
