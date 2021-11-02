package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.UpdateAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.vo.DetailAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;

import java.util.List;

public interface AdvanceSalaryEmployeeService {
    List<GetAllAdvanceSalaryEmployeeVO> searchAdvanceSalaryEmployee(SearchAdvanceEmployeeForm searchForm);
    int totalSearch(SearchAdvanceEmployeeForm searchForm);
    Boolean addAdvanceSalaryEmployee(AddAdvanceSalaryEmployeeForm addForm);
    Boolean updateAdvanceSalaryEmployee(UpdateAdvanceSalaryEmployeeForm updateForm);
    Boolean deleteAdvanceSalaryEmployee(Long id);
    DetailAdvanceSalaryEmployeeVO getDetailAdvanceSalaryEmployee(Long id);
}
