package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.SearchPersonalApplicationEmployeeForm;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;

import java.util.List;

public interface PersonalLeaveApplicationEmployeeCustomRepository {
    List<SearchPersonalApplicationEmployeeVO> searchPerLeaApplicationEmployee(SearchPersonalApplicationEmployeeForm searchForm, Long idEmployee);
    int totalSearch(SearchPersonalApplicationEmployeeForm searchForm,Long idEmployee);
}
