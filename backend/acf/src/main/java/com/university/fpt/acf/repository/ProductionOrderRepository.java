package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.ProductionOrder;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;
import com.university.fpt.acf.vo.ViewWorkDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionOrderRepository extends JpaRepository<ProductionOrder,Long> {

    @Query("select new com.university.fpt.acf.vo.ProductionOrderDetailVO(e.id,e.fullName) from  ProductionOrder po inner join po.employees e where po.id = :idProductionOrder")
    List<ProductionOrderDetailVO> getProductionOrder(@Param("idProductionOrder") Long idProduction);


    @Query("select new com.university.fpt.acf.vo.ViewWorkDetailVO(pm.id,m.name,CONCAT(fm.frameLength,'x',fm.frameWidth,'x',hm.frameHeight),gm.name, prm.count, um.name,c.name, prm.note) from Account a inner join  a.employee e inner  join e.productionOrders  po inner join po.products p inner join  p.productMaterials prm  inner  join  prm.priceMaterial pm inner  join  pm.material m inner join pm.frameMaterial fm inner  join  pm.heightMaterial hm inner  join m.groupMaterial gm inner join pm.unitMeasure um inner join m.company c ")
    List<ViewWorkDetailVO> getProductionOrderTest(@Param("idProductionOrder") Long idProduction);

}
