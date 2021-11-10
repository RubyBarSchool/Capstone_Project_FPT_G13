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
    @Query("select g.name from GroupMaterial  g where  g.id =:id")
    String getNameGroupById(@Param("id") Long id);

    @Query("select g.id from GroupMaterial  g where  g.name =:name")
    Long getIdByNameGroup(@Param("name") String name);

    @Query("select g from GroupMaterial  g where  g.id =:id")
    GroupMaterial getGroupMaterialByID(@Param("id") Long id);
    @Query("select new com.university.fpt.acf.vo.GroupMaterialVO(u.id,u.name) from GroupMaterial u")
    List<GroupMaterialVO> getAllGroups();
}
