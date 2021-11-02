package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddPerLeaveAppEmployeeForm;
import com.university.fpt.acf.form.SearchPersonalApplicationEmployeeForm;
import com.university.fpt.acf.form.UpdatePersonalAppEmployeeForm;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;


import java.util.List;

public interface PersonalLeaveApplicationEmployeeService {
    Boolean AddLeaveApplication(AddPerLeaveAppEmployeeForm addPerLeaveAppEmployeeForm);
    Boolean UpdateLeaveApplication(UpdatePersonalAppEmployeeForm updateForm);
    Boolean DeleteLeaveApplication(Long id);
    List<SearchPersonalApplicationEmployeeVO> searchPersonalLeaveApplicationEmployee(SearchPersonalApplicationEmployeeForm searchForm);
    int totalSearch(SearchPersonalApplicationEmployeeForm searchForm);
    SearchPersonalApplicationEmployeeVO detailPersonalLeaveAppEmployee(Long id);
}
