package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PriceMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceMaterialRepository extends JpaRepository<PriceMaterial, Long> {
    @Query("select p from PriceMaterial p inner join p.frameMaterial fm inner join  p.heightMaterial hm " +
            " where p.deleted = false and p.unitMeasure.id = :idUnit " +
            " and p.material.id = :idMaterial and fm.frameWidth = :width and fm.frameLength = :length " +
            " and hm.frameHeight = :height ")
    PriceMaterial getPriceMaterial(@Param("idUnit") Long idUnit, @Param("idMaterial") Long idMaterial
            , @Param("width") String width, @Param("length") String length, @Param("height") String height);
}
