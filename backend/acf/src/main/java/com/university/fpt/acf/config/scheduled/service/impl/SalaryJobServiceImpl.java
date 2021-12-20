package com.university.fpt.acf.config.scheduled.service.impl;

import com.university.fpt.acf.config.scheduled.service.SalaryJobService;
import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.repository.BonusRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.repository.HistorySalaryRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Service
public class SalaryJobServiceImpl implements SalaryJobService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private HistorySalaryRepository historySalaryRepository;
    @Autowired
    private BonusRepository bonusRepository;
    @Override
    public void payroll() {
        //************************************
        // Generate automatic payroll of employees
        // 1. Search full information of reward, discipline,
        // 2. Add a new salary history for each employee
        //************************************
        try{
            LocalDate dateStart = LocalDate.now();
            LocalDate nextMonth = dateStart.plusMonths(1);
            LocalDate dateEnd = LocalDate.of(nextMonth.getYear(),nextMonth.getMonthValue(),10);
            List<Employee> employeeList = employeeRepository.getAllEmployee();
            List<HistorySalary> historySalaries = new ArrayList<>();
            for(Employee employee : employeeList){
                HistorySalary historySalary = new HistorySalary();
                historySalary.setCreated_by("JOB_AUTO");
                historySalary.setModified_by("JOB_AUTO");
                historySalary.setCountWork(0.0);
                historySalary.setSalary(employee.getSalary()+"");
                historySalary.setEmployee(employee);
                List<BonusPenalty> bonusPenalties = bonusRepository.getBonusPenaltyOfEmployee(employee.getId(),dateStart,dateEnd);
                Integer bonus = 0;
                Integer penalty = 0;
                Integer total = 0;
                for(BonusPenalty bonusPenalty : bonusPenalties){
                    if(bonusPenalty.getBonus()){
                        bonus+=Integer.parseInt(bonusPenalty.getMoney());
                        total+=Integer.parseInt(bonusPenalty.getMoney());
                    }else{
                        penalty+=Integer.parseInt(bonusPenalty.getMoney());
                        total-=Integer.parseInt(bonusPenalty.getMoney());
                    }
                }
                historySalary.setTotalMoney(total+"");
                historySalary.setBonus(bonus+"");
                historySalary.setPenalty(penalty+"");
                historySalary.setAdvanceSalary("0");
                historySalaries.add(historySalary);
            }
            historySalaryRepository.saveAll(historySalaries);
        }catch (Exception ex){
            throw new RuntimeException("Không thêm được bảng lương");
        }
    }
}
