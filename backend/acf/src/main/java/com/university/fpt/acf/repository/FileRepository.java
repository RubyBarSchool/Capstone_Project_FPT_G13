package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface FileRepository extends JpaRepository<File,String> {

    @Modifying
    @Transactional
    @Query(" delete  from  File  f where f.id = :id")
    void deleteByID(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(" delete  from  File  f where f.name = :id")
    void deleteByName(@Param("id") String name);

    @Query(" select f from  File  f where f.id = :id")
    File getFileByID(@Param("id") String id);
}
