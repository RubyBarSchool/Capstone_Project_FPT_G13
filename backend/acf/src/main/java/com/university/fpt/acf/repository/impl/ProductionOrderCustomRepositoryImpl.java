package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.form.SearchProductionOrderForm;
import com.university.fpt.acf.form.SearchWorkEmployeeForm;
import com.university.fpt.acf.repository.ProductionOrderCustomRepository;
import com.university.fpt.acf.vo.*;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductionOrderCustomRepositoryImpl extends CommonRepository implements ProductionOrderCustomRepository {
    @Override
    public List<ProductionOrderViewWorkVO> getListWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.ProductionOrderViewWorkVO(e.id,e.fullName,po.id,po.name,po.dateStart,po.dateEnd) " +
                " from ProductionOrder po inner join po.employees e where po.deleted = false and po.status in (-1,0)  ");
        if (dateWorkEmployeeFrom.getDateStart() != null) {
            sqlAcc.append(" and po.dateEnd >= :dateStart ");
            paramsAcc.put("dateStart", dateWorkEmployeeFrom.getDateStart());
        }
        if (dateWorkEmployeeFrom.getDateEnd() != null) {
            sqlAcc.append(" and po.dateStart <=  :dateEnd ");
            paramsAcc.put("dateEnd", dateWorkEmployeeFrom.getDateEnd());
        }
        sqlAcc.append(" ORDER by e.id asc ");
        TypedQuery<ProductionOrderViewWorkVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, ProductionOrderViewWorkVO.class);
        List<ProductionOrderViewWorkVO> resultList = queryAcc.getResultList();
        resultList.addAll(this.getListNotWorkEmployee(resultList));
        return resultList;
    }

    private List<ProductionOrderViewWorkVO> getListNotWorkEmployee(List<ProductionOrderViewWorkVO> resultList){
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        sqlAcc.append(" select new com.university.fpt.acf.vo.ProductionOrderViewWorkVO(e.id,e.fullName) from Employee e inner join e.position p where e.deleted = false and p.code != 'GD'  ");
        if (resultList != null && resultList.size() != 0) {
            List<Long> idEmpl = new ArrayList<>();
            for(ProductionOrderViewWorkVO productionOrderViewWorkVO : resultList){
                idEmpl.add(productionOrderViewWorkVO.getIdEmployee());
            }
            sqlAcc.append(" and e.id not in :id ");
            paramsAcc.put("id", idEmpl);
        }
        TypedQuery<ProductionOrderViewWorkVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, ProductionOrderViewWorkVO.class);
        List<ProductionOrderViewWorkVO> resultListEmployeeNotWork = queryAcc.getResultList();
        return resultListEmployeeNotWork;
    }


    @Override
    public List<SearchProductionOrderVO> searchProductOrder(SearchProductionOrderForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.SearchProductionOrderVO(po.id,po.name,po.created_date,c.id," +
                "c.name,p.id,p.name,p.count,po.dateStart,po.dateEnd,po.status,po.numberFinish) from ProductionOrder po " +
                "inner join  po.products p inner join  p.contact c where po.deleted =false ");
        if(searchForm.getNameProduction() != null && !searchForm.getNameProduction().isEmpty()){
            sql.append(" and po.name like :name");
            params.put("name","%"+searchForm.getNameProduction()+"%");
        }
        if(searchForm.getListIdContact().size()!=0 && searchForm.getListIdContact()!=null){
            sql.append(" and c.id in :listIdContact ");
            params.put("listIdContact",searchForm.getListIdContact());
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and po.status =: status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDateList() != null && !searchForm.getDateList().isEmpty()) {
            sql.append(" and  po.dateEnd BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDateList().get(0));
            params.put("dateEnd", searchForm.getDateList().get(1));
        }
        sql.append(" ORDER by po.status asc , po.id desc");
        TypedQuery<SearchProductionOrderVO> query = super.createQuery(sql.toString(),params, SearchProductionOrderVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchProductOrder(SearchProductionOrderForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from ProductionOrder po inner join  po.products p inner join  p.contact c where po.deleted =false ");
        if(searchForm.getNameProduction() != null && !searchForm.getNameProduction().isEmpty()){
            sql.append(" and po.name like :name");
            params.put("name","%"+searchForm.getNameProduction()+"%");
        }
        if(searchForm.getListIdContact().size()!=0 && searchForm.getListIdContact()!=null){
            sql.append(" and c.id in :listIdContact ");
            params.put("listIdContact",searchForm.getListIdContact());
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and po.status = :status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDateList() != null && !searchForm.getDateList().isEmpty()) {
            sql.append(" and  po.dateEnd BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDateList().get(0));
            params.put("dateEnd", searchForm.getDateList().get(1));
        }
        sql.append(" ORDER by po.status asc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<ViewWorkVO> searchProductOrderEmployee(SearchWorkEmployeeForm searchWorkEmployeeForm, String username) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.ViewWorkVO" +
                "(po.id,po.name,p.name,CONCAT(p.length,'x',p.width,'x',p.height),p.count,po.numberFinish,po.dateStart,po.dateEnd,po.status) from Account a " +
                "inner join a.employee e inner join e.productionOrders po inner join po.products p " +
                "where a.username = :username");
        params.put("username",username);
        if(searchWorkEmployeeForm.getStatus() != null){
            sql.append(" and po.status = :status ");
            params.put("status",searchWorkEmployeeForm.getStatus());
        }
        sql.append(" ORDER by po.status asc ");
        TypedQuery<ViewWorkVO> query = super.createQuery(sql.toString(),params, ViewWorkVO.class);
        query.setFirstResult((searchWorkEmployeeForm.getPageIndex()-1)* searchWorkEmployeeForm.getPageSize());
        query.setMaxResults(searchWorkEmployeeForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchProductOrderEmployee(SearchWorkEmployeeForm searchWorkEmployeeForm, String username) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from Account a " +
                "inner join a.employee e inner join e.productionOrders po inner join po.products p " +
                "where a.username = :username");
        params.put("username",username);
        if(searchWorkEmployeeForm.getStatus() != null){
            sql.append(" and po.status = :status ");
            params.put("status",searchWorkEmployeeForm.getStatus());
        }
        sql.append(" ORDER by po.status asc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<ViewWorkDetailVO> searchProductOrderDetailEmployee(String username,Long idProducttionOrder) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.ViewWorkDetailVO" +
                "(pm.id,m.name,CONCAT(fm.frameLength,'x',fm.frameWidth,'x',hm.frameHeight),gm.name, prm.count, um.name,c.name, prm.note) " +
                " from  Account a inner join  a.employee e inner  join e.productionOrders po inner join po.products p inner join  p.productMaterials prm  " +
                " inner  join  prm.priceMaterial pm inner  join  pm.material m inner join pm.frameMaterial fm " +
                " inner  join  pm.heightMaterial hm inner  join m.groupMaterial gm inner join pm.unitMeasure um " +
                " inner join m.company c where po.id = :idProductionOrder and a.username = :username ");
        params.put("username",username);
        params.put("idProductionOrder",idProducttionOrder);
        sql.append(" ORDER by m.id asc ");
        TypedQuery<ViewWorkDetailVO> query = super.createQuery(sql.toString(),params, ViewWorkDetailVO.class);
        return query.getResultList();
    }
}
