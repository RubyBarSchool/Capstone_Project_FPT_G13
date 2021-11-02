package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddPerLeaveAppEmployeeForm;
import com.university.fpt.acf.form.UpdatePersonalAppEmployeeForm;

public interface PersonalLeaveApplicationEmployeeService {
    Boolean AddLeaveApplication(AddPerLeaveAppEmployeeForm addPerLeaveAppEmployeeForm);
    Boolean UpdateLeaveApplication(UpdatePersonalAppEmployeeForm updateForm);
    Boolean DeleteLeaveApplication(Long id);
}
