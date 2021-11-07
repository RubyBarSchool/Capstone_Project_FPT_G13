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
import com.university.fpt.acf.vo.ResultSearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<ResultSearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchBonus) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        List<ResultSearchBonusAdminVO> listResult = new ArrayList<>();
        int dem=0;
        try {
            list = punishCustomRepository.searchPunish(searchBonus);
            for(int i=dem;i<list.size();i++){
                dem++;
                List<Long> listId = new ArrayList<>();
                ResultSearchBonusAdminVO re = new ResultSearchBonusAdminVO();
                re.setId(list.get(i).getId());
                re.setMoney(list.get(i).getMoney());
                re.setReason(list.get(i).getReason());
                re.setTitle(list.get(i).getTitle());
                re.setEffectiveDate(list.get(i).getEffectiveDate());
                re.setStatus(list.get(i).getStatus());
                Long idBonus = list.get(i).getId();
                listId.add(list.get(i).getIdEmployee());
                for(int j=i+1;j< list.size();j++){
                    if(list.get(j).getId()==idBonus){
                        dem=j;
                        listId.add(list.get(j).getIdEmployee());
                    }else {
                        break;
                    }
                }
                re.setListIdEmployee(listId);
                listResult.add(re);
                if(dem<list.size()){
                    i=dem;
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error punish repository " + e.getMessage());
        }
        return listResult;
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
    @Transactional
    public Boolean addPunish(AddBonusAdminForm addPunish) {
        boolean check = false;
        try{
            BonusPenalty punish = new BonusPenalty();
            punish.setBonus(false);
            punish.setTitle(addPunish.getTitle());
            punish.setMoney(addPunish.getMoney());
            punish.setReason(addPunish.getReason());
            punish.setStatus(addPunish.getStatus());
            punish.setEffectiveDate(addPunish.getEffectiveDate());
            punish.setEmployees(employeeRepository.getEmployeeByIdS(addPunish.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            punish.setCreated_by(accountSercurity.getUserName());
            punish.setCreated_date(LocalDate.now());
            punishRepository.saveAndFlush(punish);
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
