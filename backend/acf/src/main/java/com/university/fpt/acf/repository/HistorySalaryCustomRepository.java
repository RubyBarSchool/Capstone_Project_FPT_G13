package com.university.fpt.acf.repository;


import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.vo.SearchSalaryVO;

import java.util.List;

public interface HistorySalaryCustomRepository {
    List<SearchSalaryVO> searchSalary(String username,BonusPunishForm bonusPunishForm);
    int getTotalSearchSalary(String username,BonusPunishForm bonusPunishForm);

    List<SearchSalaryVO> searchSalaryHistory(SearchSalaryForm searchSalaryForm);
    int getTotalSearchSalaryHistory(SearchSalaryForm searchSalaryForm);

    List<SearchSalaryVO> searchSalaryAccept(SearchSalaryForm searchSalaryForm);
    int getTotalSearchSalaryAccept(SearchSalaryForm searchSalaryForm);
}
