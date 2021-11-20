package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.form.SearchProductionOrderForm;
import com.university.fpt.acf.repository.ProductionOrderCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.GetCreateContactVO;
import com.university.fpt.acf.vo.ProductionOrderViewWorkVO;
import com.university.fpt.acf.vo.SearchProductionOrderVO;
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
                " from ProductionOrder po inner join po.employees e where po.deleted = false and po.status = false  ");
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
        if(searchForm.getStatus()!=null && !searchForm.getStatus().equals("")){
            sql.append(" and po.status =: status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDateList() != null && !searchForm.getDateList().isEmpty()) {
            sql.append(" and  po.dateEnd BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDateList().get(0));
            params.put("dateEnd", searchForm.getDateList().get(1));
        }
        sql.append(" ORDER by po.id desc ");
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
            sql.append(" and po.status=:status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDateList() != null && !searchForm.getDateList().isEmpty()) {
            sql.append(" and  po.dateEnd BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDateList().get(0));
            params.put("dateEnd", searchForm.getDateList().get(1));
        }
        sql.append(" ORDER by po.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
