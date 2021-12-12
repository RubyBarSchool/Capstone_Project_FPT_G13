package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.ProductionOrder;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;
import com.university.fpt.acf.vo.ViewWorkDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder,Long> {

    @Query("select new com.university.fpt.acf.vo.ProductionOrderDetailVO(e.id,e.fullName) from  ProductionOrder po inner join po.employees e where po.id = :idProductionOrder")
    List<ProductionOrderDetailVO> getProductionOrder(@Param("idProductionOrder") Long idProduction);

    @Query("select po  from  ProductionOrder po inner join  po.employees e where po.id = :id")
    ProductionOrder getProductionOrderByID(@Param("id") Long id);

    @Query("select a.username  from Account a inner join  a.employee e inner join e.productionOrders po where po.id = :id")
    List<String> getUsernameByID(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("delete from ProductionOrder po where po.id = :id")
    void deleteProductionOrderById(@Param("id") Long id);

}
