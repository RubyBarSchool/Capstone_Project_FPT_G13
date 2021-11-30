package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.File;
import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.repository.FileRepository;
import com.university.fpt.acf.repository.PositionRespository;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.service.FileStorageService;
import com.university.fpt.acf.util.EmployeeValidate.EmployeeValidate;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import com.university.fpt.acf.vo.SearchEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionRespository positionRespository;
    @Autowired
    private EmployeeCustomRepository employeeCustomRepository;

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<SearchEmployeeVO> searchEmployee(SearchAllEmployeeForm searchAllEmployeeForm) {
        List<SearchEmployeeVO> getAlEmployeeVOS = new ArrayList<>();
        try {
            getAlEmployeeVOS = employeeCustomRepository.searchEmployee(searchAllEmployeeForm);
        } catch (Exception e) {
            throw new RuntimeException("Error position repository " + e.getMessage());
        }
        return  getAlEmployeeVOS;
    }

    @Override
    public int getTotalEmployee(SearchAllEmployeeForm searchAllEmployeeForm) {
        int size = 0;
        try {
            size = employeeCustomRepository.getTotalEmployee(searchAllEmployeeForm);
        }catch (Exception e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }

    @Override
    public List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm) {
        Pageable pageable = PageRequest.of(searchEmployeeForm.getPageIndex()-1,searchEmployeeForm.getPageSize());
        List<GetAllEmployeeVO > list = employeeRepository.getTop10EmployeeNotAccount("%"+searchEmployeeForm.getName().toLowerCase()+"%",pageable);
        return list ;
    }

    @Override
    public List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        List<GetAllEmployeeVO> employeeVOS = new ArrayList<>();
        try{
            employeeVOS.addAll(employeeCustomRepository.getAllEmployeeNotAttendance(employeeNotAttendanceForm));
        }catch (Exception ex){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return employeeVOS;
    }

    @Override
    public int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        int size = 0;
        try {
            size = employeeCustomRepository.getTotalEmployeeNotAttendance(employeeNotAttendanceForm);
        }catch (Exception e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }

    @Override
    public EmployeeDetailVO getEmployeeDetailById(Long id) {
        EmployeeDetailVO em = new EmployeeDetailVO();
        try{
            em = employeeRepository.getEmployeeById(id);

        }catch (Exception e){
             e.getMessage();
        }
        return em;
    }

    @Override
    public Boolean AddEmployee(AddEmployeeForm addEmployeeForm) {
        boolean check = false;
        try{
            if(addEmployeeForm == null){
                throw new Exception("Data position is null");
            }else{
                EmployeeValidate validate = new EmployeeValidate();
                if(validate.checkFormEmail(addEmployeeForm.getEmail())&&validate.checkFormPhone(addEmployeeForm.getPhone())){
                    if(employeeRepository.checkExitPhone(addEmployeeForm.getPhone())==null &&
                            employeeRepository.checkExitEmail(addEmployeeForm.getEmail())== null){
                        String checkPosition =positionRespository.CheckExitPositionById(addEmployeeForm.getIdPosition());
                        if(checkPosition !=null && !checkPosition.isEmpty()){
                            Employee e = new Employee();
                            e.setFullName(addEmployeeForm.getFullName());
                            if(addEmployeeForm.getImage() != null && !addEmployeeForm.getImage().equals("")){
                                File file = new File();
                                file.setId(addEmployeeForm.getImage());
                                e.setImage(file);
                            }
                            e.setDob(addEmployeeForm.getDob());
                            e.setGender(addEmployeeForm.getGender());
                            e.setAddress(addEmployeeForm.getAddress());
                            e.setEmail(addEmployeeForm.getEmail());
                            e.setSalary(addEmployeeForm.getSalary());
                            e.setNation(addEmployeeForm.getNation());
                            e.setPhone(addEmployeeForm.getPhone());
                            Position p = new Position();
                            p.setId(addEmployeeForm.getIdPosition());
                            e.setPosition(p);
                            AccountSercurity accountSercurity = new AccountSercurity();
                            e.setCreated_date(LocalDate.now());;
                            e.setCreated_by(accountSercurity.getUserName());
                            Employee ex = employeeRepository.save(e);
                            check =true;
                        }else{
                            throw new Exception("Chức vụ không tồn tại!");
                        }

                    }else{
                        throw new Exception("SĐT/ Email không tồn tại!");
                    }
                }
                else {
                    throw new Exception("SĐT/Email sai quy chuẩn");
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
        return check;
    }

    @Override
    public Boolean UpdateEmployee(UpdateEmployeeForm updateEmployeeForm) {
        boolean check = false;
        try{
            if(updateEmployeeForm==null){
                throw new Exception("Data update employee is null");
            }
            String checkPosition =positionRespository.CheckExitPositionById(updateEmployeeForm.getIdPosition());
            if(checkPosition ==null || checkPosition.isEmpty()) {
                throw new Exception("Chức vụ không tồn tại!");
            }
            Employee e = employeeRepository.getEmployeeToUpdateById(updateEmployeeForm.getId());
            String fileName = "";
            if(e.getImage() != null && !e.getImage().equals("")){
                fileName = e.getImage().getId();
            }
            EmployeeValidate validate = new EmployeeValidate();
            if(e!=null){
                if(validate.checkFormEmail(updateEmployeeForm.getEmail())&&validate.checkFormPhone(updateEmployeeForm.getPhone())){
                    e.setFullName(updateEmployeeForm.getFullName());
                    if(updateEmployeeForm.getImage() != null && !updateEmployeeForm.getImage().equals("")){
                        File file = new File();
                        file.setId(updateEmployeeForm.getImage());
                        e.setImage(file);
                    }
                    e.setDob(updateEmployeeForm.getDob());
                    e.setGender(updateEmployeeForm.getGender());
                    e.setAddress(updateEmployeeForm.getAddress());
                    e.setEmail(updateEmployeeForm.getEmail());
                    e.setSalary(updateEmployeeForm.getSalary());
                    e.setNation(updateEmployeeForm.getNation());
                    e.setPhone(updateEmployeeForm.getPhone());
                    Position p = new Position();
                    p.setId(updateEmployeeForm.getIdPosition());
                    e.setPosition(p);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    e.setModified_by(accountSercurity.getUserName());
                    e.setModified_date(LocalDate.now());
                    employeeRepository.save(e);
                    if(updateEmployeeForm.getImage() != null && !updateEmployeeForm.getImage().equals("")){
                        if(fileName!= null && !fileName.equals("")) {
                            fileRepository.deleteByID(fileName);
                        }
                    }
                    check =true;
                }else {
                    throw new Exception("Employee is not format Phone/ Email!");
                    }
            }else {
                throw new Exception("Employee ko tổn tại hoặc đã bị xóa");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return check;
    }

    @Override
    public Boolean DeleteEmployee(Long id) {
        boolean check = false;
        try{
            Employee employee = employeeRepository.getEmployeeToUpdateById(id);
            if(employee != null){
                employee.setDeleted(true);
                AccountSercurity accountSercurity = new AccountSercurity();
                employee.setModified_by(accountSercurity.getUserName());
                employeeRepository.save(employee);
                check =true;
            }else {
                throw new Exception("Position is not existed");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return check;
    }

    @Override
    public List<GetAllEmployeeVO> getEmployeeSNotDelete(SearchEmployeeForm employeeForm) {
        Pageable pageable = PageRequest.of(employeeForm.getPageIndex()-1,employeeForm.getPageSize());
        List<GetAllEmployeeVO > list = employeeRepository.getEmployeeNotDelete("%"+employeeForm.getName().toLowerCase()+"%",pageable);
        return list ;
    }
}
