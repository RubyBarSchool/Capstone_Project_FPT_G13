package com.university.fpt.acf.service;

import com.university.fpt.acf.form.DateWorkEmployeeFrom;

import java.util.HashMap;

public interface ProductionOrderService {
    HashMap<String,Object> viewWorkEmployee(DateWorkEmployeeFrom dateWorkEmployeeFrom);
}
