package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.repository.SalaryCustomRepository;
import com.university.fpt.acf.service.SalaryService;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.SearchSalaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryCustomRepository salaryCustomRepository;

    @Override
    public List<SearchSalaryVO> searchSalaryHistory(SearchSalaryForm searchSalaryForm) {
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        try {
            searchSalaryVOS = salaryCustomRepository.searchSalaryHistory(searchSalaryForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return searchSalaryVOS;
    }

    @Override
    public int getTotalAllSalaryHistory(SearchSalaryForm searchSalaryForm) {
        if (searchSalaryForm.getTotal() != null && searchSalaryForm.getTotal() != 0) {
            return searchSalaryForm.getTotal().intValue();
        }
        int total = 0;
        try {
            total = salaryCustomRepository.getTotalSearchSalaryHistory(searchSalaryForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public List<SearchSalaryVO> searchSalaryAccept(SearchSalaryForm searchSalaryForm) {
        return null;
    }

    @Override
    public int getTotalAllSalaryAccept(SearchSalaryForm searchSalaryForm) {
        return 0;
    }
}
