package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Override
    public List<GetAllEmployeeVO> getAllEmployee(SearchAccountForm searchAccountForm) {
//        Sort sortable = null;
//        if (sort.equals("ASC")) {
//            sortable = Sort.by("id").ascending();
//        }
//        if (sort.equals("DESC")) {
//            sortable = Sort.by("id").descending();
//        }
//        Pageable pageable = PageRequest.of(searchAccountForm.getPageIndex(), searchAccountForm.getPageSize());


        Pageable pageable = PageRequest.of(searchAccountForm.getPageIndex()-1,searchAccountForm.getPageSize());
        List<Employee> listEmployee =employeeRepository.findByFullNameIsLike("%"+searchAccountForm.getName()+"%",pageable);
        List<GetAllEmployeeVO> getAllAccountVOS = new ArrayList<>();
        for(Employee i : listEmployee){
            GetAllEmployeeVO getAllEmployeeVO = new GetAllEmployeeVO();
            getAllEmployeeVO.setId(i.getId());
            getAllEmployeeVO.setName(i.getFullName());
            getAllAccountVOS.add(getAllEmployeeVO);
        }
        return  getAllAccountVOS;
    }

    @Override
    public List<String> getFullNameEmployeeNotAccount() {
        List<String > list = employeeRepository.getTop10EmployeeNotAccount();
        return list ;
    }
}
