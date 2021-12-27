package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.service.FileStorageService;
import com.university.fpt.acf.util.EmployeeValidate.EmployeeValidate;
import com.university.fpt.acf.vo.*;
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

    @Autowired
    private HistorySalaryRepository historySalaryRepository;

    //************************************
    // Search employee  with combination of fields: name, position, status
    //************************************
    @Override
    public List<SearchEmployeeVO> searchEmployee(SearchAllEmployeeForm searchAllEmployeeForm) {
        List<SearchEmployeeVO> getAlEmployeeVOS = new ArrayList<>();
        try {
            getAlEmployeeVOS = employeeCustomRepository.searchEmployee(searchAllEmployeeForm);
        } catch (Exception e) {
            throw new RuntimeException("Error position repository " + e.getMessage());
        }
        return getAlEmployeeVOS;
    }


    @Override
    public int getTotalEmployee(SearchAllEmployeeForm searchAllEmployeeForm) {
        int size = 0;
        try {
            size = employeeCustomRepository.getTotalEmployee(searchAllEmployeeForm);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }

    @Override
    public List<SearchEmployeeVO> searchEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm) {
        List<SearchEmployeeVO> getAlEmployeeVOS = new ArrayList<>();
        try {
            getAlEmployeeVOS = employeeCustomRepository.searchEmployeeAdd(searchAllEmployeeForm);
        } catch (Exception e) {
            throw new RuntimeException("Error position repository " + e.getMessage());
        }
        return getAlEmployeeVOS;
    }


    @Override
    public int getTotalEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm) {
        int size = 0;
        try {
            size = employeeCustomRepository.getTotalEmployeeAdd(searchAllEmployeeForm);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }



    //************************************
    //GEt fullname employee that not account
    //************************************
    @Override
    public List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm) {
        Pageable pageable = PageRequest.of(searchEmployeeForm.getPageIndex() - 1, searchEmployeeForm.getPageSize());
        List<GetAllEmployeeVO> list = employeeRepository.getTop10EmployeeNotAccount("%" + searchEmployeeForm.getName().toLowerCase() + "%", pageable);
        return list;
    }

    //************************************
    // GEt all employee that not attendance by date
    //************************************
    @Override
    public List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        List<GetAllEmployeeVO> employeeVOS = new ArrayList<>();
        try {
            employeeVOS.addAll(employeeCustomRepository.getAllEmployeeNotAttendance(employeeNotAttendanceForm));
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return employeeVOS;
    }

    @Override
    public int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        int size = 0;
        try {
            size = employeeCustomRepository.getTotalEmployeeNotAttendance(employeeNotAttendanceForm);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }

    //************************************
    // Get detail employee by id
    //************************************
    @Override
    public EmployeeDetailVO getEmployeeDetailById(Long id) {
        EmployeeDetailVO em = new EmployeeDetailVO();
        try {
            em = employeeRepository.getEmployeeById(id);

        } catch (Exception e) {
            e.getMessage();
        }
        return em;
    }

    @Override
    public EmployeeDetailVO getEmployeeDetailByUsername(String username) {
        EmployeeDetailVO em = new EmployeeDetailVO();
        try {
            em = employeeRepository.getEmployeeVOByUsername(username);

        } catch (Exception e) {
            e.getMessage();
        }
        return em;
    }

    //************************************
    // Add employee
    //************************************
    @Override
    public Boolean AddEmployee(AddEmployeeForm addEmployeeForm) {
        boolean check = false;
        try {
            if (addEmployeeForm == null) {
                throw new Exception("Data position is null");
            } else {
                EmployeeValidate validate = new EmployeeValidate();
                if (validate.checkFormEmail(addEmployeeForm.getEmail()) && validate.checkFormPhone(addEmployeeForm.getPhone())) {
                    if (employeeRepository.checkExitPhone(addEmployeeForm.getPhone()) == null && employeeRepository.checkExitEmail(addEmployeeForm.getEmail()) == null) {
                        String checkPosition = positionRespository.CheckExitPositionById(addEmployeeForm.getIdPosition());
                        if (checkPosition != null && !checkPosition.isEmpty()) {
                            Employee e = new Employee();
                            e.setFullName(addEmployeeForm.getFullName());
                            if (addEmployeeForm.getImage() != null && !addEmployeeForm.getImage().equals("")) {
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
                            e.setCreated_date(LocalDate.now());
                            ;
                            e.setCreated_by(accountSercurity.getUserName());
                            Employee ex = employeeRepository.save(e);
                            LocalDate dateNow = LocalDate.now();
                            LocalDate dateCreat = LocalDate.now();
                            if (dateNow.getDayOfMonth() >= 10) {
                                dateCreat = LocalDate.of(dateNow.getYear(), dateNow.getMonthValue(), 10);
                            } else {
                                LocalDate dateMinus = dateNow.minusMonths(1);
                                dateCreat = LocalDate.of(dateMinus.getYear(), dateMinus.getMonthValue(), 10);
                            }
                            HistorySalary historySalary = new HistorySalary();
                            historySalary.setCreated_by("JOB_AUTO");
                            historySalary.setCreated_date(dateCreat);
                            historySalary.setModified_by("JOB_AUTO");
                            historySalary.setCountWork(0.0);
                            historySalary.setSalary(ex.getSalary() + "");
                            historySalary.setEmployee(ex);
                            historySalary.setBonus("0");
                            historySalary.setPenalty("0");
                            historySalary.setAdvanceSalary("0");
                            historySalary.setTotalMoney("0");
                            historySalaryRepository.save(historySalary);
                            check = true;
                        } else {
                            throw new Exception("Chức vụ không tồn tại!");
                        }

                    } else {
                        throw new Exception("SĐT/ Email  tồn tại!");
                    }
                } else {
                    throw new Exception("SĐT/Email sai quy chuẩn");
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return check;
    }

    //************************************
    // Update employee
    //************************************
    @Override
    public Boolean UpdateEmployee(UpdateEmployeeForm updateEmployeeForm) {
        boolean check = false;
        try {
            if (updateEmployeeForm == null) {
                throw new Exception("Data update employee is null");
            }
            String checkPosition = positionRespository.CheckExitPositionById(updateEmployeeForm.getIdPosition());
            if (checkPosition == null || checkPosition.isEmpty()) {
                throw new Exception("Chức vụ không tồn tại!");
            }
            Employee e = employeeRepository.getEmployeeToUpdateById(updateEmployeeForm.getId());
            String fileName = "";
            if (e.getImage() != null && !e.getImage().equals("")) {
                fileName = e.getImage().getId();
            }
            EmployeeValidate validate = new EmployeeValidate();
            if (e != null) {
                if (validate.checkFormEmail(updateEmployeeForm.getEmail()) && validate.checkFormPhone(updateEmployeeForm.getPhone())) {
                    String phoneNew = updateEmployeeForm.getPhone();
                    String emailNew = updateEmployeeForm.getEmail();
                    if (!e.getPhone().equals(phoneNew)) {
                        if (employeeRepository.checkExitPhone(phoneNew) == null) {
                            e.setPhone(updateEmployeeForm.getPhone());
                        } else {
                            throw new Exception("SĐT tồn tại!");
                        }
                    }
                    if (!e.getEmail().equals(emailNew)) {
                        if (employeeRepository.checkExitEmail(emailNew) == null) {
                            e.setEmail(updateEmployeeForm.getEmail());
                        } else {
                            throw new Exception("Email  tồn tại!");
                        }
                    }
                    if (updateEmployeeForm.getImage() != null && !updateEmployeeForm.getImage().equals("")) {
                        File file = new File();
                        file.setId(updateEmployeeForm.getImage());
                        e.setImage(file);
                    }
                    e.setFullName(updateEmployeeForm.getFullName());
                    e.setDob(updateEmployeeForm.getDob());
                    e.setGender(updateEmployeeForm.getGender());
                    e.setAddress(updateEmployeeForm.getAddress());
                    e.setSalary(updateEmployeeForm.getSalary());
                    e.setNation(updateEmployeeForm.getNation());
                    Position p = new Position();
                    p.setId(updateEmployeeForm.getIdPosition());
                    e.setPosition(p);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    e.setModified_by(accountSercurity.getUserName());
                    e.setModified_date(LocalDate.now());
                    employeeRepository.save(e);
                    if (updateEmployeeForm.getImage() != null && !updateEmployeeForm.getImage().equals("")) {
                        if (fileName != null && !fileName.equals("")) {
                            fileRepository.deleteByID(fileName);
                        }
                    }
                    check = true;
                } else {
                    throw new Exception("Employee is not format Phone/ Email!");
                }
            } else {
                throw new Exception("Employee ko tổn tại hoặc đã bị xóa");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return check;
    }

    //************************************
    // Delete employee
    //************************************
    @Override
    public Boolean DeleteEmployee(Long id) {
        boolean check = false;
        try {
            Employee employee = employeeRepository.getEmployeeToUpdateById(id);
            if (employee != null) {
                employee.setDeleted(true);
                AccountSercurity accountSercurity = new AccountSercurity();
                employee.setModified_by(accountSercurity.getUserName());
                employeeRepository.save(employee);
                check = true;
            } else {
                throw new Exception("Position is not existed");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return check;
    }

    //************************************
    // GEt employee that not delete in system
    //************************************
    @Override
    public List<GetAllEmployeeVO> getEmployeeSNotDelete(SearchEmployeeForm employeeForm) {
        Pageable pageable = PageRequest.of(employeeForm.getPageIndex() - 1, employeeForm.getPageSize());
        List<GetAllEmployeeVO> list = employeeRepository.getEmployeeNotDelete("%" + employeeForm.getName().toLowerCase() + "%", pageable);
        return list;
    }

    //************************************
    // GEt detail employee by username
    //************************************
    @Override
    public DetailEmployeeVO getDetailEmployeeByUsername() {
        AccountSercurity accountSercurity = new AccountSercurity();
        String username = accountSercurity.getUserName();
        DetailEmployeeVO detailEmployeeVO = new DetailEmployeeVO();
        List<DetailEmployeeInformationVO> employeeInformationVO = new ArrayList<>();
        try {
            employeeInformationVO = employeeRepository.getDetailEmployee(username);
            if (employeeInformationVO == null) {
                throw new Exception("Không tìm thấy");
            }
            detailEmployeeVO.setUsername(employeeInformationVO.get(0).getUsername());
            detailEmployeeVO.setEmail(employeeInformationVO.get(0).getEmail());
            detailEmployeeVO.setAddress(employeeInformationVO.get(0).getAddress());
            detailEmployeeVO.setDob(employeeInformationVO.get(0).getDob());
            detailEmployeeVO.setImage(employeeInformationVO.get(0).getImage());
            detailEmployeeVO.setFullName(employeeInformationVO.get(0).getFullName());
            detailEmployeeVO.setGender(employeeInformationVO.get(0).getGender());
            detailEmployeeVO.setGender(employeeInformationVO.get(0).getGender());
            detailEmployeeVO.setNation(employeeInformationVO.get(0).getNation());
            detailEmployeeVO.setPhone(employeeInformationVO.get(0).getPhone());
            detailEmployeeVO.setSalary(employeeInformationVO.get(0).getSalary());
            List<String> listRole = new ArrayList<>();
            for (int i = 0; i < employeeInformationVO.size(); i++) {
                listRole.add((employeeInformationVO.get(i).getRoleName()));
            }
            detailEmployeeVO.setListRoleName(listRole);
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return detailEmployeeVO;
    }

    @Override
    public Boolean changeImage(String idFile) {
        Boolean check = false;
        AccountSercurity accountSercurity = new AccountSercurity();
        String username = accountSercurity.getUserName();
        DetailEmployeeVO detailEmployeeVO = new DetailEmployeeVO();
        Employee employee = new Employee();
        try {
            employee = employeeRepository.getEmployeeByUsername(username);
            if (employee == null) {
                throw new Exception("Không tồn tại employee  với account: " + username);
            }
            String idImage = "";
            if (employee.getImage() != null && !employee.getImage().equals("")) {
                idImage = employee.getImage().getId();
            }
            File file = new File();
            file.setId(idFile);
            employee.setImage(file);
            employeeRepository.save(employee);
            if (idImage != null && !idImage.equals("")) {
                fileRepository.deleteByID(idImage);
            }
            check = true;
        } catch (Exception ex) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return check;
    }
}
