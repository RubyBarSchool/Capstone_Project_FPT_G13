package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;
import com.university.fpt.acf.vo.SearchProductionOrderVO;
import com.university.fpt.acf.vo.ViewWorkDetailVO;
import com.university.fpt.acf.vo.ViewWorkVO;

import java.util.HashMap;
import java.util.List;

public interface ProductionOrderService {
    HashMap<String,Object> viewWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom);

    List<SearchProductionOrderVO> searchProductionOrder(SearchProductionOrderForm searchForm);
    int totalSearchProductionOrder(SearchProductionOrderForm searchForm);

    List<ViewWorkVO> searchProductionOrderEmployee(SearchWorkEmployeeForm searchWorkEmployeeForm);
    int totalSearchProductionOrderEmployee(SearchWorkEmployeeForm searchWorkEmployeeForm);

    List<ViewWorkDetailVO> searchProductionOrderDetailEmployee(Long id);

    Boolean addProductionOrder(AddProductionOrderFrom addProductionOrderFrom);
    Boolean updateProductionOrder(AddProductionOrderFrom addProductionOrderFrom);
    Boolean confirmWork(Long id);
    Boolean confirmWorkDone(Long id);
    Boolean updateWork(UpdateWorkEmployeeFrom updateWorkEmployeeFrom);
    Boolean deleteProductionOrder(Long id);
    List<ProductionOrderDetailVO> getDetailProduction(Long idProduction);
}
