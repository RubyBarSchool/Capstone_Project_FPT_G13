package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.PersonaLeaveApplication;
import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.form.AcceptPersonalLeaveApplicationAdminForm;
import com.university.fpt.acf.form.SearchPersonalLeaveAdminApplicationForm;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.repository.PersonalLeaveApplicationAdminCustomRepository;
import com.university.fpt.acf.repository.PersonalLeaveApplicationAdminRepository;
import com.university.fpt.acf.service.PersonalLeaveApplicationAdminService;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PersonalLeaveApplicationAdminServiceImpl implements PersonalLeaveApplicationAdminService {
    @Autowired
    PersonalLeaveApplicationAdminCustomRepository personalLeaveApplicationCustomRepository;
    @Autowired
    PersonalLeaveApplicationAdminRepository personalLeaveApplicationAdminRepository;
    @Autowired
    AccountManagerRepository accountRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

    //************************************
    // Search material  with combination of fields: codeMaterial, frame,group, unit, company
    //************************************
    @Override
    public List<SearchPersonalLeaveApplicationAdminVO> searchPersonalApplication(SearchPersonalLeaveAdminApplicationForm personalApplicationForm) {
        List<SearchPersonalLeaveApplicationAdminVO> listPersonalApplication = new ArrayList<>();
        try {
            listPersonalApplication = personalLeaveApplicationCustomRepository.searchApplication(personalApplicationForm);
        } catch (Exception e) {
            throw new RuntimeException("Error Personal Leave Application  repository " + e.getMessage());
        }
        return listPersonalApplication;
    }

    @Override
    public int totalPersonalApplication(SearchPersonalLeaveAdminApplicationForm personalApplicationForm) {
        int total = 0;
        try {
            total = personalLeaveApplicationCustomRepository.totalSearchApplication(personalApplicationForm);
        } catch (Exception e) {
            e.getMessage();
        }
        return total;
    }

    //************************************
    // Accept persional leave application
    //************************************
    @Override
    public Boolean acceptPersonalLeaveApplication(AcceptPersonalLeaveApplicationAdminForm acceptForm) {
        boolean check = false;
        try {
            if (acceptForm.getIdApplication() == null) {
                throw new Exception("idApplication is null");
            } else {
                PersonaLeaveApplication p = personalLeaveApplicationAdminRepository.getApplicationById(acceptForm.getIdApplication());
                if (p == null) {
                    throw new Exception(" Personal Leave Application ko ton tai ");
                }
                p.setAccept("1");
                p.setComment(acceptForm.getComment());
                p.setDateAccept(LocalDate.now());
                AccountSercurity accountSercurity = new AccountSercurity();
                Long idEm = accountRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
                p.setIdEmployeeAccept(idEm);
                p.setModified_by(accountSercurity.getUserName());
                p.setModified_date(LocalDate.now());
                personalLeaveApplicationAdminRepository.save(p);

                if (!p.getCreated_by().equals(accountSercurity.getUserName())) {
                    Notification notification = new Notification();
                    notification.setUsername(p.getCreated_by());
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" chấp nhận đơn xin nghỉ của bạn");
                    notification.setPath("/leaveapplication");
                    HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(p.getCreated_by(), "/queue/notification", dataOutPut);
                }
            }
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    //************************************
    // Reject personal leave application
    //************************************
    @Override
    public Boolean rejectPersonalLeaveApplication(AcceptPersonalLeaveApplicationAdminForm acceptForm) {
        boolean check = false;
        try {
            if (acceptForm.getIdApplication() == null) {
                throw new Exception("idApplication is  Null");
            } else {
                PersonaLeaveApplication p = personalLeaveApplicationAdminRepository.getApplicationById(acceptForm.getIdApplication());
                if (p == null) {
                    throw new Exception(" Không tìm thấy đơn! ");
                }
                p.setAccept("0");
                p.setComment(acceptForm.getComment());
                p.setDateAccept(LocalDate.now());
                AccountSercurity accountSercurity = new AccountSercurity();
                Long idEm = accountRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
                p.setIdEmployeeAccept(idEm);
                p.setModified_by(accountSercurity.getUserName());
                p.setModified_date(LocalDate.now());
                personalLeaveApplicationAdminRepository.save(p);

                if (!p.getCreated_by().equals(accountSercurity.getUserName())) {
                    Notification notification = new Notification();
                    notification.setType("error");
                    notification.setUsername(p.getCreated_by());
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" từ chối đơn xin nghỉ của bạn");
                    notification.setPath("/leaveapplication");
                    HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(p.getCreated_by(), "/queue/notification", dataOutPut);
                }
            }
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

}