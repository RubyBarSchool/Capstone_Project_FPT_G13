package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.vo.*;


import java.util.List;

public interface EmployeeService  {
    List<SearchEmployeeVO> searchEmployee(SearchAllEmployeeForm searchAllEmployeeForm);
    int getTotalEmployee(SearchAllEmployeeForm searchAllEmployeeForm);
    List<SearchEmployeeVO> searchEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm);
    int getTotalEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm);
    List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm);
    List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    EmployeeDetailVO getEmployeeDetailById(Long id);
    EmployeeDetailVO getEmployeeDetailByUsername(String username);
    Boolean AddEmployee(AddEmployeeForm addEmployeeForm);
    Boolean UpdateEmployee(UpdateEmployeeForm updateEmployeeForm);
    Boolean DeleteEmployee(Long id);
    List<GetAllEmployeeVO> getEmployeeSNotDelete( SearchEmployeeForm employeeForm);
    DetailEmployeeVO getDetailEmployeeByUsername();
    Boolean changeImage(String idFile);
}
