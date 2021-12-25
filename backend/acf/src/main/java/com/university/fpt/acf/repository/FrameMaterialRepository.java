package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.FrameMaterial;
import com.university.fpt.acf.vo.FrameMaterialVO;
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
    @Query("select f.id from FrameMaterial f where f.frameWidth=:width and f.frameLength=:length and f.deleted = false ")
    Long getIdByLengthWith(@Param("width") String width,@Param("length") String length);
    @Query("select new com.university.fpt.acf.vo.FrameMaterialVO(fm.id,concat(fm.frameLength,'x',fm.frameWidth) ) from FrameMaterial fm left join PriceMaterial p inner join p.material m where m.deleted =false and m.checkMaterial=true and fm.deleted=false and p.frameMaterial.id is null")
    List<FrameMaterialVO> getFrameMaterialToInsert();
    @Query("select new com.university.fpt.acf.vo.FrameMaterialVO(fm.id,concat(fm.frameLength,'x',fm.frameWidth) ) from FrameMaterial fm left join PriceMaterial p inner join p.material m where m.deleted =false and m.checkMaterial=false and fm.deleted=false and p.frameMaterial.id is null")
    List<FrameMaterialVO> getFrameCoverSheetToInsert();

}
