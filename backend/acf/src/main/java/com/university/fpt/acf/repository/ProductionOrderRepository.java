package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.ProductionOrder;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder,Long> {

    @Query("select new com.university.fpt.acf.vo.ProductionOrderDetailVO(e.id,e.fullName) from  ProductionOrder po inner join po.employees e where po.id = :idProductionOrder")
    List<ProductionOrderDetailVO> getProductionOrder(@Param("idProductionOrder") Long idProduction);
}
