package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Product;
import com.university.fpt.acf.vo.ProductVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    @Query("select new com.university.fpt.acf.vo.ProductVO(p.id,p.name) from Product p inner  join  p.contact c where c.id = :idContact")
    List<ProductVO> getProductInContact(@Param("idContact") Long idContact);
}
