package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.ProductionOrder;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;
import com.university.fpt.acf.vo.ReportTopEmployeeVO;
import com.university.fpt.acf.vo.ViewWorkDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Modifying
    @Transactional
    @Query("delete from ProductionOrder po where po.products.id = :id")
    void deleteProductionOrderByIdProduct(@Param("id") Long id);

    @Query("select COUNT(po.id)  from ProductionOrder po where po.deleted = false  and po.status = -2")
    Integer getProducttionOrderConfirmAdmin();

    @Query("select COUNT(po.id)  from ProductionOrder po where po.deleted = false  and po.status = -1")
    Integer getProducttionOrderConfirmEmployee();

    @Query("select COUNT(po.id)  from Account a inner  join  a.employee e inner  join  e.productionOrders po " +
            " where po.deleted = false  and po.status = -1 and a.username = :username ")
    Integer getProducttionOrderConfirmEmployeeByUsername(@Param("username") String username);

    @Query("select COUNT(po.id)  from Account a inner  join  a.employee e inner  join  e.productionOrders po " +
            " where po.deleted = false  and po.status = 1 and a.username = :username and po.modified_date >= :dateStart and po.modified_date < :dateEnd")
    Integer getProducttionOrderDoneEmployeeByUsername(@Param("username") String username,@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);

    @Query("select new com.university.fpt.acf.vo.ReportTopEmployeeVO(a.username,e.fullName,SUM(p.count))  from Account a inner  join  a.employee e inner  join  e.productionOrders po inner  join" +
            "  po.products p where po.deleted = false  and po.status = 1 and po.modified_date >= :dateStart and po.modified_date < :dateEnd group by a.username,e.fullName order by SUM(p.count) desc ")
    List<ReportTopEmployeeVO> getTopEmployee(@Param("dateStart") LocalDate dateStart, @Param("dateEnd") LocalDate dateEnd);
}
