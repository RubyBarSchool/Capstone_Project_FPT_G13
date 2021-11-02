package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.AddAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;

import java.util.List;

public interface AdvanceSalaryEmployeeCustomRepository {
    List<GetAllAdvanceSalaryEmployeeVO> searchAdvanceSalary(SearchAdvanceEmployeeForm advanceForm);
    int totalSearch(SearchAdvanceEmployeeForm advanceForm);
}
