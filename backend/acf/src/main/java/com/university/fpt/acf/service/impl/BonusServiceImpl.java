package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.UpdateBonusForm;
import com.university.fpt.acf.repository.BonusCustomRepository;
import com.university.fpt.acf.repository.BonusRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class BonusServiceImpl implements BonusService {
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private BonusCustomRepository bonusCustomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchBonus) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            list = bonusCustomRepository.searchBonus(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchBonus(SearchBonusAdminForm searchBonus) {
        int size;
        try {
            size = bonusCustomRepository.totalSearchBonus(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }

    @Override
    public List<SearchBonusAdminVO> searchBonusUser(BonusPunishForm bonusPunishForm) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = bonusCustomRepository.searchBonusUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchBonusUser(BonusPunishForm bonusPunishForm) {
        if (bonusPunishForm.getTotal() != null && bonusPunishForm.getTotal() != 0) {
            return bonusPunishForm.getTotal().intValue();
        }
        int size;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            size = bonusCustomRepository.totalSearchBonusUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }



    @Override
    @Transactional
    public Boolean addBonus(AddBonusAdminForm addBonus) {
        boolean check = false;
        try {
            BonusPenalty bonus = new BonusPenalty();
            bonus.setBonus(true);
            bonus.setTitle(addBonus.getTitle());
            bonus.setMoney(addBonus.getMoney());
            bonus.setReason(addBonus.getReason());
            bonus.setStatus(addBonus.getStatus());
            bonus.setEffectiveDate(addBonus.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(addBonus.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            bonusRepository.saveAndFlush(bonus);
            check = true;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deleteBonus(Long id) {
        boolean check = false;
        try {
            BonusPenalty bonus = bonusRepository.getBonusById(id);
            bonus.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            bonusRepository.save(bonus);
            check = true;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateBonus(UpdateBonusForm updateBonus) {
        boolean check = false;
        try {
            BonusPenalty bonus = bonusRepository.getBonusById(updateBonus.getId());
            bonus.setBonus(true);
            bonus.setTitle(updateBonus.getTitle());
            bonus.setMoney(updateBonus.getMoney());
            bonus.setReason(updateBonus.getReason());
            bonus.setStatus(updateBonus.getStatus());
            bonus.setEffectiveDate(updateBonus.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(updateBonus.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            bonusRepository.saveAndFlush(bonus);
            check = true;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

}
