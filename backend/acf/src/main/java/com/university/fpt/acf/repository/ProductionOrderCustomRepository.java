package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.DateWorkEmployeeFrom;
import com.university.fpt.acf.vo.ProductionOrderViewWorkVO;

import java.util.List;

public interface ProductionOrderCustomRepository {
    List<ProductionOrderViewWorkVO> getListWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom);
}
