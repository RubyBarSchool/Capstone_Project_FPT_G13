package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ExportExcelForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.SearchSalaryVO;

import java.util.List;

public interface SalaryCustomRepository {
    List<SearchSalaryVO> searchSalaryHistory(SearchSalaryForm searchSalaryForm);
    int getTotalSearchSalaryHistory(SearchSalaryForm searchSalaryForm);

    List<SearchSalaryVO> searchSalaryAccept(SearchSalaryForm searchSalaryForm);
    int getTotalSearchSalaryAccept(SearchSalaryForm searchSalaryForm);
}
