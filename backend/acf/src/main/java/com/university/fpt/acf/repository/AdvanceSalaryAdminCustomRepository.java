package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchAdvanceSalaryAdminForm;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;

import java.util.List;

public interface AdvanceSalaryAdminCustomRepository {
    List<SearchAdvanceSalaryAdminVO> searchAdvanceSalary(SearchAdvanceSalaryAdminForm searchForm);
    int totalSearchAdvance(SearchAdvanceSalaryAdminForm searchForm);
}
