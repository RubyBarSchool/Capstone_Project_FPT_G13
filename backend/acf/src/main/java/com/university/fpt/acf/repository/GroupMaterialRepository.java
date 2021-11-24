package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.GroupMaterial;
import com.university.fpt.acf.entity.UnitMeasure;
import com.university.fpt.acf.vo.GroupMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMaterialRepository extends JpaRepository<GroupMaterial, Long> {
    @Query("select g.name from GroupMaterial  g where g.checkGroupMaterial=true and g.id =:id and g.deleted =false")
    String getNameGroupById(@Param("id") Long id);

    @Query("select g.id from GroupMaterial  g where g.checkGroupMaterial=true and g.name =:name and g.deleted =false")
    Long getIdByNameGroup(@Param("name") String name);

    @Query("select g from GroupMaterial  g where g.checkGroupMaterial = true and  g.id =:id and g.deleted =false")
    GroupMaterial getGroupMaterialByID(@Param("id") Long id);
    @Query("select new com.university.fpt.acf.vo.GroupMaterialVO(u.id,u.name) from GroupMaterial u  where u.checkGroupMaterial=true and u.deleted=false ")
    List<GroupMaterialVO> getAllGroups();

    @Query("select g.name from GroupMaterial  g where g.checkGroupMaterial=false and g.id =:id and g.deleted =false")
    String getNameGroupCoverPlateById(@Param("id") Long id);

    @Query("select g.id from GroupMaterial  g where g.checkGroupMaterial=false and g.name =:name and g.deleted =false")
    Long getIdCoverPlateByNameGroup(@Param("name") String name);

    @Query("select g from GroupMaterial  g where g.checkGroupMaterial=false and  g.id =:id and g.deleted =false ")
    GroupMaterial getGroupCoverPlateByID(@Param("id") Long id);
    @Query("select new com.university.fpt.acf.vo.GroupMaterialVO(u.id,u.name) from GroupMaterial u  where u.checkGroupMaterial=false and u.deleted = false ")
    List<GroupMaterialVO> getAllGroupsCoverPlate();
}
