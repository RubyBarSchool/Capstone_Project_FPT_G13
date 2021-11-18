package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.repository.ProductionOrderCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.ProductionOrderViewWorkVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
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
        return resultList;
    }
}
