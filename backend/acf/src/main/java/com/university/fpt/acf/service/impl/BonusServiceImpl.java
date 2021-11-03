package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.repository.BonusCustomRepository;
import com.university.fpt.acf.repository.BonusRepository;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public Boolean addBonus(AddBonusAdminForm addBonus) {
        boolean check = false;
        try{
            BonusPenalty bonus = new BonusPenalty();
            bonus.setBonus(addBonus.isBonus());
            bonus.setTitle(addBonus.getTitle());
            bonus.setMoney(addBonus.getMoney());
            bonus.setReason(addBonus.getReason());
//            bonus.setStatus(addBonus.getStatus());
            bonus.setEffectiveDate(addBonus.getEffectiveDate());
            List<Employee> listEm = new ArrayList<>();
            for(Long id :addBonus.getListIdEmployee()) {
                Employee e = new Employee();
                e.setId(id);
                listEm.add(e);
            }
            bonus.setEmployees(listEm);
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setCreated_date(LocalDate.now());
            bonusRepository.save(bonus);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

}
