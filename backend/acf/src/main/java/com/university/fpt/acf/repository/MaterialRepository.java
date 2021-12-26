package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Material;
import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.vo.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    @Query("select  m from Material m left join  m.image img where m.checkMaterial=true and m.deleted= false  and m.id=:id")
    Material getMaterialById(@Param("id") Long id);

    @Query("select  m from Material m  where  m.deleted= false  and m.name  in :names")
    List<Material> getMaterialByNameS(@Param("names") List<String> name);

    @Query("select  m from Material m left join  m.image img where m.checkMaterial=false and m.deleted= false  and m.id=:id")
    Material getCoverSheetById(@Param("id") Long id);

    @Query("SELECT new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted=false and m.checkMaterial =true ")
    List<GetAllMaterialVO> getAllMaterial();

    @Query("SELECT new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted=false and m.checkMaterial =false ")
    List<GetAllMaterialVO> getAllCoverSheet();

    @Query("SELECT new com.university.fpt.acf.vo.UnitMeasureVO(u.id,u.name) FROM UnitMeasure u where u.deleted = false and u.id not in (select distinct pm.unitMeasure.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=true and mm.id=:id and mm.deleted=false )")
    List<UnitMeasureVO> getUnitSByMaterial(@Param("id") Long id);

    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted = false and m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.unitMeasure.id=:id and mm.checkMaterial=true and mm.deleted=false ) and m.checkMaterial=true and m.deleted=false ")
    List<GetAllMaterialVO> getMaterialByUnit(@Param("id") Long id);

    @Query("SELECT new com.university.fpt.acf.vo.UnitMeasureVO(u.id,u.name) FROM UnitMeasure u where u.deleted=false and u.id not in (select distinct pm.unitMeasure.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=false and mm.id=:id and mm.deleted=false )")
    List<UnitMeasureVO> getUnitSByCoverSheet(@Param("id") Long id);

    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted = false and m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.unitMeasure.id=:id and mm.checkMaterial=false and mm.deleted = false ) and m.checkMaterial=false and m.deleted=false ")
    List<GetAllMaterialVO> getCoverSheetByUnit(@Param("id") Long id);

    @Query("SELECT new com.university.fpt.acf.vo.HeightMaterialVO(u.id,u.frameHeight) FROM HeightMaterial u where u.deleted = false and u.id not in (select distinct pm.heightMaterial.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=true and mm.id=:idMaterial and mm.deleted=false and pm.frameMaterial.id=:idFrame )   and u.deleted=false ")
    List<HeightMaterialVO> getHeightSByMaterialAndFrame(@Param("idMaterial") Long idMaterial, @Param("idFrame") Long idFrame);

    @Query("SELECT new com.university.fpt.acf.vo.HeightMaterialVO(u.id,u.frameHeight) FROM HeightMaterial u where u.deleted = false and u.id not in (select distinct pm.heightMaterial.id from Material mm inner join mm.priceMaterials  pm where mm.checkMaterial=false and mm.id=:idMaterial and mm.deleted=false and pm.frameMaterial.id=:idFrame ) and u.deleted=false ")
    List<HeightMaterialVO> getHeightByCoverSheetAndFrame(@Param("idMaterial") Long idMaterial, @Param("idFrame") Long idFrame);

    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted = false  and m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=true and mm.deleted=false and pm.frameMaterial.id=:idFrame ) and m.checkMaterial=true and m.deleted=false  ")
    List<GetAllMaterialVO> getMaterialByHeightFrame(@Param("idHeight") Long idHeight, @Param("idFrame") Long idFrame);

    @Query("select new com.university.fpt.acf.vo.GetAllMaterialVO(m.id,m.name) from Material m where m.deleted = false  and m.id not in (select distinct mm.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=false and mm.deleted=false and pm.frameMaterial.id=:idFrame ) and m.checkMaterial=false and m.deleted=false  ")
    List<GetAllMaterialVO> getCoverSheetByHeightFrame(@Param("idHeight") Long idHeight, @Param("idFrame") Long idFrame);

    @Query("select new com.university.fpt.acf.vo.FrameMaterialVO(m.id,concat(m.frameLength,'x',m.frameWidth) ) from FrameMaterial m where m.deleted = false and m.id not in (select distinct pm.frameMaterial.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=true and mm.deleted=false and mm.id=:idMaterial) and m.deleted = false")
    List<FrameMaterialVO> getFrameByMaterialAndHeight(@Param("idMaterial") Long idMaterial,@Param("idHeight") Long idHeight);

    @Query("select new com.university.fpt.acf.vo.FrameMaterialVO(m.id,concat(m.frameLength,'x',m.frameWidth) ) from FrameMaterial m  where m.deleted = false and m.id not in (select distinct pm.frameMaterial.id from Material mm inner join mm.priceMaterials  pm  where pm.heightMaterial.id=:idHeight and mm.checkMaterial=false and mm.deleted=false and mm.id=:idCoverSheet ) and m.deleted = false")
    List<FrameMaterialVO> getFrameByCoverSheetAndHeight(@Param("idCoverSheet") Long idCoverSheet,@Param("idHeight") Long idHeight);

    @Query("select new com.university.fpt.acf.vo.MaterialSuggestVO(pm.material.id,SUM(prm.count*p.count)) from Contact c inner join c.products p inner  join  p.productMaterials prm inner join  prm.priceMaterial pm where c.statusDone = 1 and c.dateFinish between :dateStart and :dateEnd group by pm.material.id order by pm.material.id asc")
    List<MaterialSuggestVO> getMaterialSuggest(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Query("select m from Material m where m.id in :id  order by m.id asc")
    List<Material> getMaterialByIds(@Param("id") List<Long> id);
}

