package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;

import java.util.List;

public interface AdvanceSalaryEmployeeCustomRepository {
    List<GetAllAdvanceSalaryEmployeeVO> searchAdvanceSalary(SearchAdvanceEmployeeForm advanceForm,Long idEmployee);
    int totalSearch(SearchAdvanceEmployeeForm advanceForm,Long idEmployee);
}
