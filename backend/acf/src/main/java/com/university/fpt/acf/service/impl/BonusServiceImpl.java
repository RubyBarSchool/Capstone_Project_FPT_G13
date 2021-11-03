package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.repository.BonusCustomRepository;
import com.university.fpt.acf.repository.BonusRepository;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BonusServiceImpl implements BonusService {
    @Autowired
    private  BonusRepository bonusRepository;
    @Autowired
    private BonusCustomRepository bonusCustomRepository;
    @Override
    public List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchBonus) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            list = bonusCustomRepository.searchBonus(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return  list;
    }

    @Override
    public int totalSearchBonus(SearchBonusAdminForm searchBonus) {
        int size;
        try {
            size = bonusCustomRepository.totalSearchBonus(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return  size;
    }
}
