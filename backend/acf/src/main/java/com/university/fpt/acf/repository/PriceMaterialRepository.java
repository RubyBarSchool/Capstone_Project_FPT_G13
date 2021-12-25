package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PriceMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceMaterialRepository extends JpaRepository<PriceMaterial, Long> {
    @Query("select p from PriceMaterial p inner join p.material m inner join p.frameMaterial fm inner join  p.heightMaterial hm inner join p.unitMeasure um  " +
            " where p.deleted = false and um.id = :idUnit  and m.id = :idMaterial and fm.frameWidth = :width and fm.frameLength = :length " +
            " and hm.frameHeight = :height ")
    PriceMaterial getPriceMaterial(@Param("idUnit") Long idUnit, @Param("idMaterial") Long idMaterial
            , @Param("width") String width, @Param("length") String length, @Param("height") String height);

    @Query("select pm from PriceMaterial pm inner join  pm.material m where m.checkMaterial =true and m.deleted = false and m.id=:id")
    List<PriceMaterial> getListPriceMaterialById(@Param("id")Long id);
    @Query("select pm from PriceMaterial pm inner join  pm.material m where m.checkMaterial =false and m.deleted = false and m.id=:id")
    List<PriceMaterial> getListPriceCoverSheetById(@Param("id")Long id);

    @Query("SELECT p from PriceMaterial p inner join p.material m left join m.image img where m.checkMaterial = true and m.deleted = false and p.id=:id")
    PriceMaterial getPriceMaterialById(@Param("id")Long id);

    @Query("SELECT p from PriceMaterial p inner join p.material m where m.checkMaterial = false and m.deleted = false and p.id=:id")
    PriceMaterial getPriceCoverSheetById(@Param("id")Long id);
    @Query("select p.id from PriceMaterial p inner join p.material m where m.checkMaterial=true  and m.deleted=false and p.material.id=:idMaterial and p.frameMaterial.id=:idFrame and p.heightMaterial.id=:idHeight")
    List<Long> getIdPriceMaterialByFrameHeightMaterial(@Param("idMaterial")Long idMaterial,@Param("idFrame")Long idFrame,@Param("idHeight")Long idHeight);
    @Query("select p.id from PriceMaterial p inner join p.material m where m.checkMaterial=false  and m.deleted=false and p.material.id=:idMaterial and p.frameMaterial.id=:idFrame and p.heightMaterial.id=:idHeight")
    List<Long> getIdPriceMaterialByFrameHeightCoverSheet(@Param("idMaterial")Long idMaterial,@Param("idFrame")Long idFrame,@Param("idHeight")Long idHeight);

    @Query("select distinct pm.unitMeasure.id from PriceMaterial pm inner join  pm.material m where m.checkMaterial =true and m.deleted = false and m.id=:id")
    List<Long> getListIdUnitPriceMaterialById(@Param("id")Long id);
    @Query("select distinct pm.unitMeasure.id from PriceMaterial pm inner join  pm.material m where m.checkMaterial =false and m.deleted = false and m.id=:id")
    List<Long> getListIdUnitPriceCoverSheetById(@Param("id")Long id);

    @Query("SELECT p from PriceMaterial p inner join p.material m left join m.image img where m.deleted = false and p.id=:id")
    PriceMaterial getPriceMaterialByIdTwo(@Param("id")Long id);

}
