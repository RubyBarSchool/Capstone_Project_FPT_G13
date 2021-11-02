package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.PersonaLeaveApplication;
import com.university.fpt.acf.form.AddPerLeaveAppEmployeeForm;
import com.university.fpt.acf.form.SearchAdvanceEmployeeForm;
import com.university.fpt.acf.form.SearchPersonalApplicationEmployeeForm;
import com.university.fpt.acf.form.UpdatePersonalAppEmployeeForm;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.PersonalLeaveApplicationEmployeeService;
import com.university.fpt.acf.vo.SearchPersonalApplicationEmployeeVO;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Override
    public Boolean AddLeaveApplication(AddPerLeaveAppEmployeeForm addPerLeaveAppEmployeeForm) {
        boolean check = false;
        try{
            PersonaLeaveApplication p = new PersonaLeaveApplication();
            String em = employeeRepository.getFullNameById(addPerLeaveAppEmployeeForm.getIdEmployee());
            if(em==null || em.isEmpty()){
                throw new Exception("Nhan vien ko ton tai");
            }
            p.setTitle(addPerLeaveAppEmployeeForm.getTitle());
            p.setFileAttach(addPerLeaveAppEmployeeForm.getFileAttach());
            p.setContent(addPerLeaveAppEmployeeForm.getContent());
            p.setDateStart(addPerLeaveAppEmployeeForm.getDate().get(0));
            p.setDateEnd(addPerLeaveAppEmployeeForm.getDate().get(1));
            Employee e = new Employee();
            e.setId(addPerLeaveAppEmployeeForm.getIdEmployee());
            p.setEmployee(e);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setCreated_date(LocalDate.now());
            personalLeaveApplicationEmployeeRepository.save(p);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean UpdateLeaveApplication(UpdatePersonalAppEmployeeForm updateForm) {
        boolean check = false;
        try{
            PersonaLeaveApplication p = personalLeaveApplicationAdminRepository.getApplicationById(updateForm.getIdApplication());
            if(p==null){
                throw new Exception("Không tìm thấy đơn");
            }
            String em = employeeRepository.getFullNameById(updateForm.getIdEmployee());
            if(em==null || em.isEmpty()){
                throw new Exception("Nhan vien ko ton tai");
            }
            p.setTitle(updateForm.getTitle());
            p.setFileAttach(updateForm.getFileAttach());
            p.setContent(updateForm.getContent());
            p.setDateStart(updateForm.getDate().get(0));
            p.setDateEnd(updateForm.getDate().get(1));
            Employee e = new Employee();
            e.setId(updateForm.getIdEmployee());
            p.setEmployee(e);
            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            personalLeaveApplicationAdminRepository.save(p);
            check=true;

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

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

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public List<SearchPersonalApplicationEmployeeVO> searchPersonalLeaveApplicationEmployee(SearchPersonalApplicationEmployeeForm searchForm) {
        List<SearchPersonalApplicationEmployeeVO> list = new ArrayList<>();
        try{
            list =personalCustomRepository.searchPerLeaApplicationEmployee(searchForm);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearch(SearchPersonalApplicationEmployeeForm searchForm) {
        int size;
        try{
            size = personalCustomRepository.totalSearch(searchForm);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return size;
    }


}
