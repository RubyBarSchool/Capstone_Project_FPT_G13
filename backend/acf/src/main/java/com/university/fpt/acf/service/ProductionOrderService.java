package com.university.fpt.acf.service;

import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.form.SearchProductionOrderForm;
import com.university.fpt.acf.vo.SearchProductionOrderVO;

import java.util.HashMap;
import java.util.List;

public interface ProductionOrderService {
    HashMap<String,Object> viewWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom);

    List<SearchProductionOrderVO> searchProductionOrder(SearchProductionOrderForm searchForm);
    int totalSearchProductionOrder(SearchProductionOrderForm searchForm);
}
