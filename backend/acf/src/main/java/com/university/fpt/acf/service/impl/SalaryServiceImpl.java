package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchSalaryForm;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.HistorySalaryCustomRepository;
import com.university.fpt.acf.repository.HistorySalaryRepository;
import com.university.fpt.acf.service.SalaryService;
import com.university.fpt.acf.vo.SearchSalaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private HistorySalaryCustomRepository historySalaryCustomRepository;

    @Autowired
    private HistorySalaryRepository historySalaryRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private AccountManagerRepository accountManagerRepository;
    //************************************
    // Search salary  with combination of fields:date , check now
    //************************************
    @Override
    public List<SearchSalaryVO> searchSalary(BonusPunishForm bonusPunishForm) {
        List<SearchSalaryVO> searchSalaryVOS = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            searchSalaryVOS = historySalaryCustomRepository.searchSalary(accountSercurity.getUserName(), bonusPunishForm);
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
            AccountSercurity accountSercurity = new AccountSercurity();
            total = historySalaryCustomRepository.getTotalSearchSalary(accountSercurity.getUserName(), bonusPunishForm);
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
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            HistorySalary historySalary = historySalaryRepository.getById(id);
            if (historySalary != null) {
                historySalary.setStatus(true);
                historySalary.setModified_by(accountSercurity.getUserName());
                historySalary.setAccountAccept(accountSercurity.getUserName());
                historySalary.setDateAccept(LocalDate.now());
                historySalary.setModified_date(LocalDate.now());
                historySalaryRepository.save(historySalary);
                String username = accountManagerRepository.getUsername(historySalary.getEmployee().getId());
                if(!username.equals(accountSercurity.getUserName())){
                    Notification notification = new Notification();
                    notification.setUsername(username);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" đã thanh toán lương cho bạn");
                    notification.setPath("/viewsalary");
                    HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(username, "/queue/notification", dataOutPut);
                }
            } else {
                throw new RuntimeException("id not exit");
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
