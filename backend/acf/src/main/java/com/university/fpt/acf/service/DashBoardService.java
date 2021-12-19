package com.university.fpt.acf.service;

import com.university.fpt.acf.vo.DashboardAdmin;
import com.university.fpt.acf.vo.DashboardEmployee;
import com.university.fpt.acf.vo.ReportTopEmployeeVO;

import java.util.HashMap;
import java.util.List;

public interface DashBoardService {
    Integer countEmployeeHaveNotAccount();
    DashboardAdmin getDataDashboardAdmin();
    DashboardEmployee getDataDashboardEmployee();
    List<HashMap<String,Object>> getDataReportContact();
    List<ReportTopEmployeeVO> getTopEmployee();
}
