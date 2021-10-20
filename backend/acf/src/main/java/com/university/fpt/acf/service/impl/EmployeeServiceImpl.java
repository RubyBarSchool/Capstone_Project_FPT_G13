package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.EmployeeNotAttendanceForm;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.form.SearchEmployeeForm;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
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

    @Autowired
    private EmployeeCustomRepository employeeCustomRepository;
//    @Override
//    public List<GetAllEmployeeVO> getAllEmployee(SearchEmployeeForm searchEmployeeForm) {
//
//
//        Pageable pageable = PageRequest.of(searchEmployeeForm.getPageIndex()-1,searchEmployeeForm.getPageSize());
//        List<Employee> listEmployee =employeeRepository.findByFullNameIsLike("%"+searchEmployeeForm.getName()+"%",pageable);
//        List<GetAllEmployeeVO> getAllAccountVOS = new ArrayList<>();
//        for(Employee i : listEmployee){
//            GetAllEmployeeVO getAllEmployeeVO = new GetAllEmployeeVO();
//            getAllEmployeeVO.setId(i.getId());
//            getAllEmployeeVO.setName(i.getFullName());
//            getAllAccountVOS.add(getAllEmployeeVO);
//        }
//        return  getAllAccountVOS;
//    }

    @Override
    public List<GetAllEmployeeVO> getFullNameEmployeeNotAccount(SearchEmployeeForm searchEmployeeForm) {
        Pageable pageable = PageRequest.of(searchEmployeeForm.getPageIndex()-1,searchEmployeeForm.getPageSize());
        List<GetAllEmployeeVO > list = employeeRepository.getTop10EmployeeNotAccount("%"+searchEmployeeForm.getName().toLowerCase()+"%",pageable);
        return list ;
    }

    @Override
    public List<GetAllEmployeeVO> getAllEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        List<GetAllEmployeeVO> employeeVOS = new ArrayList<>();
        try{
            employeeVOS.addAll(employeeCustomRepository.getAllEmployeeNotAttendance(employeeNotAttendanceForm));
        }catch (Exception ex){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return employeeVOS;
    }

    @Override
    public int getTotalEmployeeNotAttendance(EmployeeNotAttendanceForm employeeNotAttendanceForm) {
        int size = 0;
        try {
            size = employeeCustomRepository.getTotalEmployeeNotAttendance(employeeNotAttendanceForm);
        }catch (Exception e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }
}
