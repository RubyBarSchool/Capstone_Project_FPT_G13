package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.repository.PunishCustomRepository;
import com.university.fpt.acf.repository.PunishRepository;
import com.university.fpt.acf.service.PunishService;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PunishServiceImpl implements PunishService {
    @Autowired
    private PunishRepository punishRepository;
    @Autowired
    private PunishCustomRepository punishCustomRepository;
    @Override
    public List<SearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchBonus) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            list = punishCustomRepository.searchPunish(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error punish repository " + e.getMessage());
        }
        return  list;
    }

    @Override
    public int totalSearchPunish(SearchBonusAdminForm searchBonus) {
        return 0;
    }

    @Override
    public Boolean addPunish(AddBonusAdminForm addBonus) {
        return null;
    }

    @Override
    public Boolean deletePunish(Long id) {
        return null;
    }
}
