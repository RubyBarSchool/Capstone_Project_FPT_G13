package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.EmployeeNotAttendanceForm;
import com.university.fpt.acf.form.SearchAllEmployeeForm;
import com.university.fpt.acf.vo.DetailEmployeeInformationVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import com.university.fpt.acf.vo.SearchEmployeeVO;

import java.util.List;

public interface EmployeeCustomRepository {
    List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    List<GetAllEmployeeVO> getAllEmployeeNotAttendanceJob();
    int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    List<SearchEmployeeVO> searchEmployee(SearchAllEmployeeForm searchAllEmployeeForm);
    int getTotalEmployee(SearchAllEmployeeForm searchAllEmployeeForm);
    List<SearchEmployeeVO> searchEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm);
    int getTotalEmployeeAdd(SearchAllEmployeeForm searchAllEmployeeForm);
}
