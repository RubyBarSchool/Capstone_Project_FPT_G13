package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.PersonaLeaveApplication;
import com.university.fpt.acf.form.AddPerLeaveAppEmployeeForm;
import com.university.fpt.acf.form.SearchPersonalApplicationEmployeeForm;
import com.university.fpt.acf.form.UpdatePersonalAppEmployeeForm;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.PersonalLeaveApplicationEmployeeService;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PersonalLeaveApplicationEmployeeServiceImpl implements PersonalLeaveApplicationEmployeeService {
    @Autowired
    private  PersonalLeaveApplicationAdminRepository personalLeaveApplicationAdminRepository;
    @Autowired
    private  PersonalLeaveApplicationEmployeeRepository personalLeaveApplicationEmployeeRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PersonalLeaveApplicationEmployeeCustomRepository personalCustomRepository;
    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;
    //************************************
    // Add persional leave application
    //************************************
    @Override
    public Boolean AddLeaveApplication(AddPerLeaveAppEmployeeForm addPerLeaveAppEmployeeForm) {
        boolean check = false;
        try{
            PersonaLeaveApplication p = new PersonaLeaveApplication();
            AccountSercurity accountSercurity = new AccountSercurity();
            Long idEmployee =accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
//            String em = employeeRepository.getFullNameById(idEmployee);
            if(idEmployee==null ){
                throw new Exception("Nhan vien ko ton tai");
            }
            p.setTitle(addPerLeaveAppEmployeeForm.getTitle());
            p.setFileAttach(addPerLeaveAppEmployeeForm.getFileAttach());
            p.setContent(addPerLeaveAppEmployeeForm.getContent());
            p.setDateStart(addPerLeaveAppEmployeeForm.getDate().get(0));
            p.setDateEnd(addPerLeaveAppEmployeeForm.getDate().get(1));
            Employee e = new Employee();
            e.setId(idEmployee);
            p.setEmployee(e);
            p.setCreated_by(accountSercurity.getUserName());
            p.setModified_by(accountSercurity.getUserName());
            personalLeaveApplicationEmployeeRepository.save(p);
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
                notification.setContent(" tạo một đơn xin nghỉ");
                notification.setPath("/acceptleaveapplication");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Update Persional Leave application
    //************************************
    @Override
    public Boolean UpdateLeaveApplication(UpdatePersonalAppEmployeeForm updateForm) {
        boolean check = false;
        try{
            PersonaLeaveApplication p = personalLeaveApplicationAdminRepository.getApplicationById(updateForm.getIdApplication());
            if(p==null){
                throw new Exception("Không tìm thấy đơn");
            }
            AccountSercurity accountSercurity = new AccountSercurity();
            Long idEmployee =accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            String em = employeeRepository.getFullNameById(idEmployee);
            if(em==null || em.isEmpty()){
                throw new Exception("Nhan vien ko ton tai");
            }
            p.setTitle(updateForm.getTitle());
            p.setFileAttach(updateForm.getFileAttach());
            p.setContent(updateForm.getContent());
            p.setDateStart(updateForm.getDate().get(0));
            p.setDateEnd(updateForm.getDate().get(1));
            Employee e = new Employee();
            e.setId(idEmployee);
            p.setEmployee(e);
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            personalLeaveApplicationAdminRepository.save(p);
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
                notification.setContent(" chỉnh sửa một đơn xin nghỉ");
                notification.setPath("/acceptleaveapplication");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Delete Persional Leave Applycation
    //************************************
    @Override
    public Boolean DeleteLeaveApplication(Long id) {
        boolean check = false;
        try{
            PersonaLeaveApplication p = personalLeaveApplicationAdminRepository.getApplicationById(id);
            if(p==null){
                throw new Exception("Không tìm thấy đơn");
            }
            p.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            personalLeaveApplicationAdminRepository.save(p);
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
                notification.setContent(" xóa một đơn xin nghỉ");
                notification.setPath("/acceptleaveapplication");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Search persional leave application  with combination of fields:title, status, date
    //************************************
    @Override
    public List<SearchPersonalApplicationEmployeeVO> searchPersonalLeaveApplicationEmployee(SearchPersonalApplicationEmployeeForm searchForm) {
        List<SearchPersonalApplicationEmployeeVO> list = new ArrayList<>();
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Long idEmployee = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            list =personalCustomRepository.searchPerLeaApplicationEmployee(searchForm,idEmployee);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearch(SearchPersonalApplicationEmployeeForm searchForm) {
        int size;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Long idEmployee = accountManagerRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
            size = personalCustomRepository.totalSearch(searchForm,idEmployee);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return size;
    }
    //************************************
    // Get detail persional leave application employee
    //************************************
    @Override
    public SearchPersonalApplicationEmployeeVO detailPersonalLeaveAppEmployee(Long id) {
        SearchPersonalApplicationEmployeeVO data = new SearchPersonalApplicationEmployeeVO();
        try {
            data = personalLeaveApplicationEmployeeRepository.detailPersonalLeaveAppEmployee(id);
        } catch (Exception e) {
            throw new RuntimeException("Error Personal Leave Application Employee  repository " + e.getMessage());
        }
        return  data;

    }



}
