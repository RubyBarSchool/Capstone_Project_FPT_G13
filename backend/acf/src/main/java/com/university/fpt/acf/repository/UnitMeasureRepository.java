package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.UnitMeasure;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitMeasureRepository extends JpaRepository<UnitMeasure,Long> {
    @Query("select u.name from UnitMeasure  u where  u.id =:id and u.deleted=false")
    String getNameUnitById(@Param("id") Long id);

    @Query("select u.id from UnitMeasure  u where  u.name =:name and u.deleted=false")
    Long getIdByName(@Param("name") String name);

    @Query("select u from UnitMeasure  u where  u.id =:id and u.deleted=false ")
    UnitMeasure getUnitByID(@Param("id") Long id);

    @Query("select new com.university.fpt.acf.vo.UnitMeasureVO(u.id,u.name) from UnitMeasure u where u.deleted = false ")
    List<UnitMeasureVO> getAllUnit();
    @Query("SELECT new  com.university.fpt.acf.vo.UnitMeasureVO(h.id,h.name) from UnitMeasure h left join PriceMaterial p inner join p.material m where m.checkMaterial=true and m.deleted =false and  p.unitMeasure.id is null and h.deleted=false ")
    List<UnitMeasureVO> getUnitsMaterialToInsert();
    @Query("SELECT new  com.university.fpt.acf.vo.UnitMeasureVO(h.id,h.name) from UnitMeasure h left join PriceMaterial p inner join p.material m where m.checkMaterial=false and m.deleted =false and  p.unitMeasure.id is null and h.deleted=false ")
    List<UnitMeasureVO> getUnitsCoverSheetToInsert();
}