package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Product;
import com.university.fpt.acf.vo.ProductVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select new com.university.fpt.acf.vo.ProductVO(p.id,p.name) from Product p inner  join  p.contact c where c.id = :idContact and p.status = -2")
    List<ProductVO> getProductInContact(@Param("idContact") Long idContact);

    @Query("select new com.university.fpt.acf.vo.ProductVO(p.id,p.name) from Product p inner  join  p.contact c where c.id = :idContact ")
    List<ProductVO> getProductInContactAll(@Param("idContact") Long idContact);

    @Query("select p from Product p where p.id = :id")
    Product getProductByID(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from Product  p where p.id = :id")
    void deleteProductInContact(@Param("id") Long id);

    @Query("select COUNT(p.id) from Product p left join p.productionOrder po where p.deleted = false and po.id is null ")
    Integer getProductHaveNotD();
}
