package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;


import java.util.List;

public interface EmployeeService  {
    //List<GetAllEmployeeVO> getAllEmployee(SearchEmployeeForm searchEmployeeForm);
    List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm);
    List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    EmployeeDetailVO getEmployeeDetailById(Long id);
    Boolean AddEmployee(AddEmployeeForm addEmployeeForm);
    Boolean UpdateEmployee(UpdateEmployeeForm updateEmployeeForm);
    Boolean DeleteEmployee(Long id);
}
