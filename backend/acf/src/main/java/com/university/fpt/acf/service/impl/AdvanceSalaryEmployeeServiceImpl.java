package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.AdvaceSalary;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.AddAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.UpdateAdvanceSalaryEmployeeForm;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.AdvanceSalaryEmployeeCustomRepository;
import com.university.fpt.acf.repository.AdvanceSalaryEmployeeRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.AdvanceSalaryEmployeeService;
import com.university.fpt.acf.vo.DetailAdvanceSalaryEmployeeVO;
import com.university.fpt.acf.vo.GetAllAdvanceSalaryEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AdvanceSalaryEmployeeServiceImpl implements AdvanceSalaryEmployeeService {
    @Autowired
    private AdvanceSalaryEmployeeRepository advanceSalaryEmployeeRepository;

    @Autowired
    private AdvanceSalaryEmployeeCustomRepository advanceSalaryEmployeeCustomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;
    //************************************
    // Search advance salary  with combination of fields: title, content, status, date
    //************************************
    @Override
    public List<GetAllAdvanceSalaryEmployeeVO> searchAdvanceSalaryEmployee(SearchAdvanceEmployeeForm searchForm) {
        List<GetAllAdvanceSalaryEmployeeVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            Long id = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            list = advanceSalaryEmployeeCustomRepository.searchAdvanceSalary(searchForm,id);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  list;
    }
    //************************************
    // Get total search advance salary
    //************************************
    @Override
    public int totalSearch(SearchAdvanceEmployeeForm searchForm) {
       int size;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            Long id = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            size = advanceSalaryEmployeeCustomRepository.totalSearch(searchForm,id);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  size;
    }
    //************************************
    // Add advance salary
    //************************************
    @Override
    public Boolean addAdvanceSalaryEmployee(AddAdvanceSalaryEmployeeForm addForm) {
        boolean check = false;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Long id = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            AdvaceSalary p = new AdvaceSalary();
            String em = employeeRepository.getFullNameById(id);
            if(em==null || em.isEmpty()){
                throw new Exception("Nhân viên không tồn tại");
            }
            p.setTitle(addForm.getTitle());
            p.setContent(addForm.getContent());
            p.setAdvaceSalary(addForm.getAdvanceSalary());
            Employee e = new Employee();
            e.setId(id);
            p.setEmployee(e);
            p.setCreated_by(accountSercurity.getUserName());
            p.setModified_by(accountSercurity.getUserName());
            advanceSalaryEmployeeRepository.save(p);
            check=true;

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for(String s : accountAdmin){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" Tạo một đơn xin ứng lương");
                notification.setPath("/acceptadvancesalary");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Update advance salary
    //************************************
    @Override
    public Boolean updateAdvanceSalaryEmployee(UpdateAdvanceSalaryEmployeeForm updateForm) {
        boolean check = false;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Long id = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            AdvaceSalary p = advanceSalaryEmployeeRepository.getAdvanceSalaryById(updateForm.getId());
            String em = employeeRepository.getFullNameById(id);
            if(em==null || em.isEmpty()){
                throw new Exception("Nhân viên không tồn tại");
            }
            p.setTitle(updateForm.getTitle());
            p.setContent(updateForm.getContent());
            p.setAdvaceSalary(updateForm.getAdvanceSalary());
            Employee e = new Employee();
            e.setId(id);
            p.setEmployee(e);
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            advanceSalaryEmployeeRepository.save(p);
            check=true;

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for(String s : accountAdmin){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" chỉnh sửa một đơn xin ứng lương");
                notification.setPath("/acceptadvancesalary");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Delete advance salary
    //************************************
    @Override
    public Boolean deleteAdvanceSalaryEmployee(Long id) {
        boolean check = false;
        try{
            AdvaceSalary p = advanceSalaryEmployeeRepository.getAdvanceSalaryById(id);
            p.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            advanceSalaryEmployeeRepository.save(p);
            check=true;

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for(String s : accountAdmin){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" xóa một đơn xin ứng lương");
                notification.setPath("/acceptadvancesalary");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Get detail advance salary employee by id
    //************************************
    @Override
    public DetailAdvanceSalaryEmployeeVO getDetailAdvanceSalaryEmployee(Long id) {
        DetailAdvanceSalaryEmployeeVO data = new DetailAdvanceSalaryEmployeeVO();
        try {
            data = advanceSalaryEmployeeRepository.getDetailAdvanceSalaryEmployeeByIdAplication(id);
        } catch (Exception e) {
            throw new RuntimeException("Error Advance Salary repository " + e.getMessage());
        }
        return  data;
    }
}
