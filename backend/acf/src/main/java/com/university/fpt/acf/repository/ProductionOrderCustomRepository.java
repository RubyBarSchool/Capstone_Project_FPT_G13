package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.form.SearchProductionOrderForm;
import com.university.fpt.acf.vo.ProductionOrderViewWorkVO;
import com.university.fpt.acf.vo.SearchProductionOrderVO;

import java.util.List;

public interface ProductionOrderCustomRepository {
    List<ProductionOrderViewWorkVO> getListWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom);

    List<SearchProductionOrderVO>  searchProductOrder(SearchProductionOrderForm searchForm);
    int totalSearchProductOrder(SearchProductionOrderForm searchForm);
}
