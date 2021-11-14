package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Material;
import com.university.fpt.acf.vo.GetAllMaterialVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Long> {
    @Query("select  m from Material m where m.checkMaterial=true and m.deleted= false  and m.id=:id")
    Material getMaterialById(@Param("id")Long id);
    @Query("select  m from Material m where m.checkMaterial=false and m.deleted= false  and m.id=:id")
    Material getCoverSheetById(@Param("id")Long id);
    @Query("SELECT new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted=false and m.checkMaterial =true ")
    GetAllMaterialVO getAllMaterialInserted();
    @Query("SELECT new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted=false and m.checkMaterial =false ")
    GetAllMaterialVO getAllCoverSheetInserted();
}

