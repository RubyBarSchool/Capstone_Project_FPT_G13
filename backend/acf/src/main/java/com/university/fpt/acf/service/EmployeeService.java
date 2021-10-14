package com.university.fpt.acf.service;

import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.form.SearchEmployeeForm;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.vo.GetAllEmployeeVO;


import java.util.List;

public interface EmployeeService  {
    //List<GetAllEmployeeVO> getAllEmployee(SearchEmployeeForm searchEmployeeForm);
    List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm);
}
