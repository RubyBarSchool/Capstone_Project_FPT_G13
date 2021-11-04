package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.UpdateBonusForm;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.repository.PunishCustomRepository;
import com.university.fpt.acf.repository.PunishRepository;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.service.PunishService;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PunishServiceImpl implements PunishService {
    @Autowired
    private PunishRepository punishRepository;
    @Autowired
    private PunishCustomRepository punishCustomRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
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
        int size;
        try {
            size = punishCustomRepository.totalSearchPunish(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return  size;
    }

    @Override
    public List<SearchBonusAdminVO> searchPunishUser(BonusPunishForm bonusPunishForm) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = punishCustomRepository.searchPunishUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchPunishUser(BonusPunishForm bonusPunishForm) {
        if (bonusPunishForm.getTotal() != null && bonusPunishForm.getTotal() != 0) {
            return bonusPunishForm.getTotal().intValue();
        }
        int size;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            size = punishCustomRepository.totalSearchPunishUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }

    @Override
    public Boolean addPunish(AddBonusAdminForm addBonus) {
        boolean check = false;
        try{
            BonusPenalty bonus = new BonusPenalty();
            bonus.setBonus(false);
            bonus.setTitle(addBonus.getTitle());
            bonus.setMoney(addBonus.getMoney());
            bonus.setReason(addBonus.getReason());
            bonus.setStatus(addBonus.getStatus());
            bonus.setEffectiveDate(addBonus.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(addBonus.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            punishRepository.saveAndFlush(bonus);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deletePunish(Long id) {
        boolean check = false;
        try{
            BonusPenalty bonus = punishRepository.getPunishById(id);
            bonus.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            punishRepository.save(bonus);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updatePunish(UpdateBonusForm updateForm) {
        boolean check = false;
        try{
            BonusPenalty bonus = punishRepository.getPunishById(updateForm.getId());
            bonus.setBonus(false);
            bonus.setTitle(updateForm.getTitle());
            bonus.setMoney(updateForm.getMoney());
            bonus.setReason(updateForm.getReason());
            bonus.setStatus(updateForm.getStatus());
            bonus.setEffectiveDate(updateForm.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(updateForm.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            punishRepository.saveAndFlush(bonus);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}
