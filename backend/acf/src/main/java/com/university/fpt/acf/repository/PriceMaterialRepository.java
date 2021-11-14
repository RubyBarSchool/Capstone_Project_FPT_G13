package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.PriceMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceMaterialRepository extends JpaRepository<PriceMaterial,Long> {
}
