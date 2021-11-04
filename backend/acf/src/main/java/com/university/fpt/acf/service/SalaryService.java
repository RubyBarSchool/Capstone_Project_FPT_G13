package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.vo.SearchSalaryVO;

import java.util.List;

public interface SalaryService {
    List<SearchSalaryVO> searchSalary(BonusPunishForm bonusPunishForm);
    int getTotalAllSalary(BonusPunishForm bonusPunishForm);

    List<SearchSalaryVO> searchSalaryHistory(SearchSalaryForm searchSalaryForm);
    int getTotalAllSalaryHistory(SearchSalaryForm searchSalaryForm);

    List<SearchSalaryVO> searchSalaryAccept(SearchSalaryForm searchSalaryForm);
    int getTotalAllSalaryAccept(SearchSalaryForm searchSalaryForm);

    Boolean acceptSalary(Long id);
}
