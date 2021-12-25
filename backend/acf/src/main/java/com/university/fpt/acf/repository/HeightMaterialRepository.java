package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.HeightMaterial;
import com.university.fpt.acf.vo.HeightMaterialVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeightMaterialRepository extends JpaRepository<HeightMaterial,Long> {
    @Query("select h.frameHeight from HeightMaterial h where h.id=:id and h.deleted=false ")
    String getHeightById(@Param("id") Long id);
    @Query("select h.id from HeightMaterial h where h.frameHeight=:frameHeight and h.deleted=false ")
    String getIdByHeight(@Param("frameHeight") String height);
    @Query("select new  com.university.fpt.acf.vo.HeightMaterialVO(h.id,h.frameHeight) from HeightMaterial h where h.deleted=false order by h.id DESC ")
    List<HeightMaterialVO> getAllHeight();
    @Query("select h from HeightMaterial h where h.id=:id and h.deleted=false ")
    HeightMaterial getHeightMaterialById(@Param("id") Long id);
    @Query("SELECT e FROM HeightMaterial e where  e.id  in :id")
    List<HeightMaterial> getHeightByIdS(@Param("id") List<Long> ids);
    @Query("SELECT new  com.university.fpt.acf.vo.HeightMaterialVO(h.id,h.frameHeight) from HeightMaterial h left join PriceMaterial p inner join p.material m where h.deleted = false and m.checkMaterial=true and m.deleted =false and  p.heightMaterial.id is null ")
    List<HeightMaterialVO> getHeightMaterialToInsert();
    @Query("SELECT new  com.university.fpt.acf.vo.HeightMaterialVO(h.id,h.frameHeight) from HeightMaterial h left join PriceMaterial p inner join p.material m where h.deleted = false and m.checkMaterial=false and m.deleted =false and  p.heightMaterial.id is null ")
    List<HeightMaterialVO> getHeightCoverSheetToInsert();
}
