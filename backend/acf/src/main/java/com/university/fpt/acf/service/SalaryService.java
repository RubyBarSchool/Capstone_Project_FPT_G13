package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.vo.SearchSalaryVO;

import java.util.List;

public interface SalaryService {
    List<SearchSalaryVO> searchSalary(SearchSalaryForm searchSalaryForm);
    int getTotalAllAttendance(SearchSalaryForm searchSalaryForm);
}
