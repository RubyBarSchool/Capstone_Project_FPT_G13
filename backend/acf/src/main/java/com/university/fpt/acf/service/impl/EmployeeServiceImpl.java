package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<GetAllEmployeeVO> getAllEmployee(SearchAccountForm searchAccountForm) {
        Page<Employee> employees =(Page<Employee>) employeeRepository.findByContent(searchAccountForm.getName(), PageRequest.of(searchAccountForm.getPageIndex(),searchAccountForm.getPageSize()));
        List<GetAllEmployeeVO> getAllAccountVOS = new ArrayList<>();
        for(Employee i : employees){
            GetAllEmployeeVO getAllEmployeeVO = new GetAllEmployeeVO();
            getAllEmployeeVO.setId(i.getId());
            getAllEmployeeVO.setName(i.getFullName());
            getAllAccountVOS.add(getAllEmployeeVO);
        }
        return  getAllAccountVOS;
    }
}
