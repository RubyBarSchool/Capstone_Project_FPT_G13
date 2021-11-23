package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.repository.HistorySalaryCustomRepository;
import com.university.fpt.acf.repository.HistorySalaryRepository;
import com.university.fpt.acf.service.SalaryService;
import com.university.fpt.acf.vo.SearchSalaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private HistorySalaryCustomRepository historySalaryCustomRepository;

    @Autowired
    private HistorySalaryRepository historySalaryRepository;

    @Override
    public List<SearchSalaryVO> searchSalary(BonusPunishForm bonusPunishForm) {
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        try {
            searchSalaryVOS = historySalaryCustomRepository.searchSalary(bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return searchSalaryVOS;
    }

    @Override
    public int getTotalAllSalary(BonusPunishForm bonusPunishForm) {
        if (bonusPunishForm.getTotal() != null && bonusPunishForm.getTotal() != 0) {
            return bonusPunishForm.getTotal().intValue();
        }
        int total = 0;
        try {
            total = historySalaryCustomRepository.getTotalSearchSalary(bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public List<SearchSalaryVO> searchSalaryHistory(SearchSalaryForm searchSalaryForm) {
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        try {
            searchSalaryVOS = historySalaryCustomRepository.searchSalaryHistory(searchSalaryForm);
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
            total = historySalaryCustomRepository.getTotalSearchSalaryHistory(searchSalaryForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public List<SearchSalaryVO> searchSalaryAccept(SearchSalaryForm searchSalaryForm) {
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        try {
            searchSalaryVOS = historySalaryCustomRepository.searchSalaryAccept(searchSalaryForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return searchSalaryVOS;
    }

    @Override
    public int getTotalAllSalaryAccept(SearchSalaryForm searchSalaryForm) {
        if (searchSalaryForm.getTotal() != null && searchSalaryForm.getTotal() != 0) {
            return searchSalaryForm.getTotal().intValue();
        }
        int total = 0;
        try {
            total = historySalaryCustomRepository.getTotalSearchSalaryAccept(searchSalaryForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    @Transactional
    public Boolean acceptSalary(Long id) {
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            HistorySalary historySalary = historySalaryRepository.getById(id);
            if(historySalary != null){
                historySalary.setStatus(true);
                historySalary.setModified_by(accountSercurity.getUserName());
                historySalary.setAccountAccept(accountSercurity.getUserName());
                historySalary.setDateAccept(LocalDate.now());
                historySalary.setModified_date(LocalDate.now());
            }else {
                throw new RuntimeException("id not exit");
            }
            historySalaryRepository.save(historySalary);
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
