package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial,Long> {

    @Modifying
    @Transactional
    @Query("delete from ProductMaterial  pm where pm.product.id = :id ")
    void deleteByIdProduct(@Param("id") Long id);
}
