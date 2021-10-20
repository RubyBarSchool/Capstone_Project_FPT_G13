package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.EmployeeNotAttendanceForm;
import com.university.fpt.acf.vo.GetAllEmployeeVO;

import java.util.List;

public interface EmployeeCustomRepository {
    List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
    int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm);
}
