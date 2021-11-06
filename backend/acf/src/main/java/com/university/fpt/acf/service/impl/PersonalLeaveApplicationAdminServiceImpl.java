package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalLeaveApplicationAdminServiceImpl implements PersonalLeaveApplicationAdminService {
    @Autowired
    PersonalLeaveApplicationAdminCustomRepository personalLeaveApplicationCustomRepository;
    @Autowired
    PersonalLeaveApplicationAdminRepository personalLeaveApplicationAdminRepository;
    @Autowired
    AccountManagerRepository accountRepository;
    @Override
    public List<SearchPersonalLeaveApplicationAdminVO> searchPersonalApplication(SearchPersonalLeaveAdminApplicationForm personalApplicationForm) {
        List<SearchPersonalLeaveApplicationAdminVO> listPersonalApplication = new ArrayList<>();
        try {
            listPersonalApplication = personalLeaveApplicationCustomRepository.searchApplication(personalApplicationForm);
        } catch (Exception e) {
            throw new RuntimeException("Error Personal Leave Application  repository " + e.getMessage());
        }
        return  listPersonalApplication;
    }

    @Override
    public int totalPersonalApplication(SearchPersonalLeaveAdminApplicationForm personalApplicationForm) {
        int total=0;
        try {
            total = personalLeaveApplicationCustomRepository.totalSearchApplication(personalApplicationForm);
        }catch (Exception e){
            e.getMessage();
        }
        return total;
    }

    @Override
    public Boolean acceptPersonalLeaveApplication(AcceptPersonalLeaveApplicationAdminForm acceptForm) {
        boolean check = false;
        try{
            if(acceptForm.getIdApplication()==null ){
                throw new Exception("Data accept Personal Leave Application is null");
            }else{
                PersonaLeaveApplication p = personalLeaveApplicationAdminRepository.getApplicationById(acceptForm.getIdApplication());
                if(p==null){
                    throw new Exception(" Personal Leave Application ko ton tai ");
                }
                p.setAccept("1");
                p.setComment(acceptForm.getComment());
                AccountSercurity accountSercurity = new AccountSercurity();
                Long idEm = accountRepository.getIdEmployeeByUsername(accountSercurity.getUserName());
                p.setIdEmployeeAccept(idEm);
                p.setModified_by(accountSercurity.getUserName());
                p.setCreated_by(accountSercurity.getUserName());
                personalLeaveApplicationAdminRepository.save(p);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public SearchPersonalLeaveApplicationAdminVO detailPersonalApplicationById(Long id) {
        SearchPersonalLeaveApplicationAdminVO data = new SearchPersonalLeaveApplicationAdminVO();
        try {
            data = personalLeaveApplicationAdminRepository.detailPersonLeaveApplication(id);
        } catch (Exception e) {
            throw new RuntimeException("Error Personal Leave Application  repository " + e.getMessage());
        }
        return  data;

    }
}
