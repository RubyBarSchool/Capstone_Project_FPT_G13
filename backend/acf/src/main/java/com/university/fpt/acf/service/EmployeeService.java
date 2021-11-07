package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import com.university.fpt.acf.vo.SearchEmployeeVO;


import java.util.List;

public interface EmployeeService  {
    List<SearchEmployeeVO> searchEmployee(SearchAllEmployeeForm searchAllEmployeeForm);
    int getTotalEmployee(SearchAllEmployeeForm searchAllEmployeeForm);
    List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm);
    List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    EmployeeDetailVO getEmployeeDetailById(Long id);
    Boolean AddEmployee(AddEmployeeForm addEmployeeForm);
    Boolean UpdateEmployee(UpdateEmployeeForm updateEmployeeForm);
    Boolean DeleteEmployee(Long id);
    List<GetAllEmployeeVO> getEmployeeSNotDelete();
}
