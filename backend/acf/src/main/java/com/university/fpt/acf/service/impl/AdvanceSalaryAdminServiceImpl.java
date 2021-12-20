package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.AdvaceSalary;

import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.form.AcceptAdvanceSalaryAdminForm;
import com.university.fpt.acf.form.SearchAdvanceSalaryAdminForm;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.AdvanceSalaryAdminCustomRepository;
import com.university.fpt.acf.repository.AdvanceSalaryAdminRepository;
import com.university.fpt.acf.repository.HistorySalaryRepository;
import com.university.fpt.acf.service.AdvanceSalaryAdminService;
import com.university.fpt.acf.vo.DetailAdvanceSalaryAdminVO;
import com.university.fpt.acf.vo.SearchAdvanceSalaryAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AdvanceSalaryAdminServiceImpl implements AdvanceSalaryAdminService {
    @Autowired
    private AdvanceSalaryAdminCustomRepository adminCustomRepository;
    @Autowired
    private AdvanceSalaryAdminRepository adminRepository;
    @Autowired
    private AccountManagerRepository accountManagerRepository;
    @Autowired
    private HistorySalaryRepository historySalaryRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

    //************************************
    // Search Advance salary with combination of fields: title, nameEmployee, status, date
    //************************************
    @Override
    public List<SearchAdvanceSalaryAdminVO> searchAdvanceSalaryAdmin(SearchAdvanceSalaryAdminForm searchForm) {
        List<SearchAdvanceSalaryAdminVO> list = new ArrayList<>();
        try {
            list =adminCustomRepository.searchAdvanceSalary(searchForm);
        }catch (Exception e){
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return list;
    }
    //************************************
    //Get total search advance salary
    //************************************
    @Override
    public int totalSearch(SearchAdvanceSalaryAdminForm searchForm) {
        int size;
        try {
            size = adminCustomRepository.totalSearchAdvance(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  size;
    }
    //************************************
    // Accept advance salary
    //************************************
    @Override
    public Boolean acceptAddvanceSalary(AcceptAdvanceSalaryAdminForm acceptForm) {
        boolean check = false;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Long id = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            AdvaceSalary data = adminRepository.getDetailAdvanceSalaryById(acceptForm.getId());
            data.setAccept("1");
            data.setDateAccept(LocalDate.now());
            data.setIdEmployeeAccept(id);
            data.setComment(acceptForm.getComment());
            data.setModified_by(accountSercurity.getUserName());
            data.setModified_date(LocalDate.now());
            adminRepository.save(data);
            check=true;

            if(!data.getCreated_by().equals(accountSercurity.getUserName())){
                Notification notification = new Notification();
                notification.setUsername(data.getCreated_by());
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" chấp nhận đơn xin ứng lương của bạn");
                notification.setPath("/advancesalary");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(data.getCreated_by(), "/queue/notification", dataOutPut);
            }

            LocalDate date = LocalDate.now();
            if(date.getDayOfMonth() < 10){
                date = date.minusMonths(1);
                date = LocalDate.of(date.getYear(),date.getMonthValue(),10);
            }else{
                date = LocalDate.of(date.getYear(),date.getMonthValue(),10);

            }

            Long idEmpl = accountManagerRepository.getIdEmployeeByUsername(data.getCreated_by());
            HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl, date);
            historySalary.setAdvanceSalary((Integer.parseInt(historySalary.getAdvanceSalary())+Integer.parseInt(data.getAdvaceSalary()))+"");
            historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.parseInt(data.getAdvaceSalary()))+"");
            historySalary.setModified_by(accountSercurity.getUserName());
            historySalary.setModified_date(LocalDate.now());
            historySalaryRepository.save(historySalary);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Reject advance salary
    //************************************
    @Override
    public Boolean rejectAdvanceSalary(AcceptAdvanceSalaryAdminForm acceptForm) {
        boolean check = false;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Long id = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            AdvaceSalary data = adminRepository.getDetailAdvanceSalaryById(acceptForm.getId());
            data.setAccept("0");
            data.setDateAccept(LocalDate.now());
            data.setIdEmployeeAccept(id);
            data.setComment(acceptForm.getComment());
            data.setModified_by(accountSercurity.getUserName());
            data.setModified_date(LocalDate.now());
            adminRepository.save(data);
            check=true;

            if(!data.getCreated_by().equals(accountSercurity.getUserName())){
                Notification notification = new Notification();
                notification.setType("error");
                notification.setUsername(data.getCreated_by());
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" không chấp nhận đơn xin ứng lương của bạn");
                notification.setPath("/advancesalary");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(data.getCreated_by(), "/queue/notification", dataOutPut);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}
