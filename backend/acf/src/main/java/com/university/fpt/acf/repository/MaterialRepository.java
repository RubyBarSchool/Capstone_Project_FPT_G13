package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Material;
import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.vo.FrameMaterialVO;
import com.university.fpt.acf.vo.GetAllMaterialVO;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Long> {
    @Query("select  m from Material m where m.checkMaterial=true and m.deleted= false  and m.id=:id")
    Material getMaterialById(@Param("id")Long id);
    @Query("select  m from Material m where m.checkMaterial=false and m.deleted= false  and m.id=:id")
    Material getCoverSheetById(@Param("id")Long id);
    @Query("SELECT new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted=false and m.checkMaterial =true ")
    List<GetAllMaterialVO> getAllMaterial();
    @Query("SELECT new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted=false and m.checkMaterial =false ")
    List<GetAllMaterialVO> getAllCoverSheet();

    @Query("SELECT new com.university.fpt.acf.vo.UnitMeasureVO(u.id,u.name) FROM UnitMeasure u where u.id not in (select distinct pm.unitMeasure.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=true and mm.id=:id and mm.deleted=false )")
    List<UnitMeasureVO>getUnitSByMaterial(@Param("id")Long id);
    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.unitMeasure.id=:id and mm.checkMaterial=true and mm.deleted=false ) and m.checkMaterial=true and m.deleted=false ")
    List<GetAllMaterialVO> getMaterialByUnit(@Param("id") Long id);
    @Query("SELECT new com.university.fpt.acf.vo.UnitMeasureVO(u.id,u.name) FROM UnitMeasure u where u.id not in (select distinct pm.unitMeasure.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=false and mm.id=:id and mm.deleted=false )")
    List<UnitMeasureVO>getUnitSByCoverSheet(@Param("id")Long id);
    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.unitMeasure.id=:id and mm.checkMaterial=false and mm.deleted = false ) and m.checkMaterial=false and m.deleted=false ")
    List<GetAllMaterialVO> getCoverSheetByUnit(@Param("id") Long id);

    @Query("SELECT new com.university.fpt.acf.vo.HeightMaterialVO(u.id,u.frameHeight) FROM HeightMaterial u where u.id not in (select distinct pm.heightMaterial.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=true and mm.id=:idMaterial and mm.deleted=false and pm.frameMaterial.id=:idFrame )")
    List<HeightMaterialVO>getHeightSByMaterialAndFrame(@Param("idMaterial")Long idMaterial, @Param("idFrame")Long idFrame);
    @Query("SELECT new com.university.fpt.acf.vo.HeightMaterialVO(u.id,u.frameHeight) FROM HeightMaterial u where u.id not in (select distinct pm.heightMaterial.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=false and mm.id=:idMaterial and mm.deleted=false and pm.frameMaterial.id=:idFrame )")
    List<HeightMaterialVO>getHeightByCoverSheetAndFrame(@Param("idMaterial")Long idMaterial,@Param("idFrame")Long idFrame);
    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=true and mm.deleted=false and pm.frameMaterial.id=:idFrame ) and m.checkMaterial=false and m.deleted=false  ")
    List<GetAllMaterialVO> getMaterialByHeightFrame(@Param("idHeight") Long idHeight,@Param("idFrame") Long idFrame);
    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=false and mm.deleted=false and pm.frameMaterial.id=:idFrame ) and m.checkMaterial=false and m.deleted=false  ")
    List<GetAllMaterialVO> getCoverSheetByHeightFrame(@Param("idHeight") Long idHeight,@Param("idFrame") Long idFrame);
    @Query("select new com.university.fpt.acf.vo.FrameMaterialVO(m.id,concat(m.frameLength,'x',m.frameWidth) ) from FrameMaterial m where m.id not in (select distinct pm.frameMaterial.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=false and mm.deleted=false and mm.id=:idMaterial and mm.checkMaterial=true ) ")
    List<FrameMaterialVO> getFrameByMaterialAndHeight(@Param("idHeight") Long idHeight, @Param("idMaterial") Long idMaterial);
    @Query("select new com.university.fpt.acf.vo.FrameMaterialVO(m.id,concat(m.frameLength,'x',m.frameWidth) ) from FrameMaterial m where m.id not in (select distinct pm.frameMaterial.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=false and mm.deleted=false and mm.id=:idCoverSheet and mm.checkMaterial=false ) ")
    List<FrameMaterialVO> getFrameByCoverSheetAndHeight(@Param("idHeight") Long idHeight,@Param("idCoverSheet") Long idCoverSheet);


}

