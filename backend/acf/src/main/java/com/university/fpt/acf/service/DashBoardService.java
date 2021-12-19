package com.university.fpt.acf.service;

import com.university.fpt.acf.vo.DashboardAdmin;
import com.university.fpt.acf.vo.DashboardEmployee;

public interface DashBoardService {
    Integer countEmployeeHaveNotAccount();
    DashboardAdmin getDataDashboardAdmin();
    DashboardEmployee getDataDashboardEmployee();
}
