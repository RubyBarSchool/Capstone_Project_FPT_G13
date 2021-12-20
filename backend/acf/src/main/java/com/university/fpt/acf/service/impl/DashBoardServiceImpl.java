package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.DashBoardService;
import com.university.fpt.acf.service.ProductService;
import com.university.fpt.acf.vo.DashboardAdmin;
import com.university.fpt.acf.vo.DashboardEmployee;
import com.university.fpt.acf.vo.ReportTopEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class DashBoardServiceImpl implements DashBoardService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AdvanceSalaryAdminRepository  advanceSalaryAdminRepository;

    @Autowired
    private PersonalLeaveApplicationAdminRepository personalLeaveApplicationAdminRepository;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private BonusRepository bonusRepository;

    @Autowired
    private PunishRepository punishRepository;
    //************************************
    // Count employee dont have account
    //************************************
    @Override
    public Integer countEmployeeHaveNotAccount() {
        Integer outPut = 0;
        try{
            outPut = employeeRepository.getEmployeeNotAccount();
        }catch (Exception e){
            throw new RuntimeException("Không lấy được thông tin nhân viên chưa có tài khoản");
        }
        return outPut;
    }
    //************************************
    // Get data dashboard Admin
    //************************************
    @Override
    public DashboardAdmin getDataDashboardAdmin() {
        DashboardAdmin dashboardAdmin = new DashboardAdmin();
        try{
            dashboardAdmin.setProductNotProductionOrder(productRepository.getProductHaveNotD());
            dashboardAdmin.setContactDone(contactRepository.getContactDone());
            dashboardAdmin.setAdvaceSalaryConfirm(advanceSalaryAdminRepository.getAdvanceSalaryConfirm());
            dashboardAdmin.setPersonaLeaveApplicationConfirm(personalLeaveApplicationAdminRepository.getPersonaLeaveApplicationConfirm());
            dashboardAdmin.setProducttionOrderConfirmAdmin(productionOrderRepository.getProducttionOrderConfirmAdmin());
            dashboardAdmin.setProducttionOrderConfirmEmployee(productionOrderRepository.getProducttionOrderConfirmEmployee());
        }catch (Exception e){
            throw new RuntimeException("Không lấy được thông tin thống kê của quản lý");
        }
        return dashboardAdmin;
    }
    //************************************
    // Get data dashboard employee
    //************************************
    @Override
    public DashboardEmployee getDataDashboardEmployee() {
        DashboardEmployee dashboardEmployee = new DashboardEmployee();
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            LocalDate dateNow = LocalDate.now();
            LocalDate dateStart = LocalDate.now();
            LocalDate dateEnd = LocalDate.now();
            if(dateNow.getDayOfMonth()<10){
                LocalDate dateTemp = dateNow.minusMonths(1);
                dateStart = LocalDate.of(dateTemp.getYear(),dateTemp.getMonthValue(),10);
                dateEnd = LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),10);
            }else{
                LocalDate dateTemp = dateNow.plusMonths(1);
                dateStart = LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),10);
                dateEnd = LocalDate.of(dateTemp.getYear(),dateTemp.getMonthValue(),10);
            }
            List<TimeKeep> timeKeeps = attendanceRepository.getTimeKeepInMonth(accountSercurity.getUserName(),dateStart,dateEnd);
            Double aDouble = 0d;
            for(TimeKeep timeKeep : timeKeeps){
                aDouble+= Double.valueOf(timeKeep.getType());
            }
            dashboardEmployee.setTimeKeep(aDouble);
            dashboardEmployee.setWorkConfirm(productionOrderRepository.getProducttionOrderConfirmEmployeeByUsername(accountSercurity.getUserName()));
            dashboardEmployee.setWorkDone(productionOrderRepository.getProducttionOrderDoneEmployeeByUsername(accountSercurity.getUserName(), dateStart,dateEnd));
            dashboardEmployee.setBonus(bonusRepository.getBonusInMonth(accountSercurity.getUserName(), dateStart,dateEnd));
            dashboardEmployee.setPunish(punishRepository.getPunishInMonth(accountSercurity.getUserName(), dateStart,dateEnd));
        }catch (Exception e){
            throw new RuntimeException("Không lấy được thông tin thống kê của nhân viên");
        }
        return dashboardEmployee;
    }
    //************************************
    // Get data Report contract
    //************************************
    @Override
    public  List<HashMap<String,Object>> getDataReportContact() {
        try{
            List<Contact> contacts = contactRepository.getReportContact();
            HashMap<String,Object> stringObjectHashMap = new HashMap<>();
            LocalDate date = LocalDate.now();
            List<HashMap<String,Object>> hashMaps = new ArrayList<>();
            for(Contact contact : contacts){
                stringObjectHashMap = new HashMap<>();
                LocalDate dateStart = contact.getCreated_date();
                LocalDate dateFinish = contact.getDateFinish();
                String numberFinish = contact.getNumberFinish();
                int index = numberFinish.indexOf("/");
                Integer numberProductDone = Integer.valueOf(numberFinish.substring(0,index));
                Integer numberProduct = Integer.valueOf(numberFinish.substring(index+1,numberFinish.length()));
                String name = contact.getName();

                Long timeContact = ChronoUnit.DAYS.between(dateStart,dateFinish);
                Long timeContactNow = ChronoUnit.DAYS.between(dateStart,date);

                Integer productExpected =  (timeContactNow.intValue()*numberProduct/timeContact.intValue());
                stringObjectHashMap.put("x",name);
                stringObjectHashMap.put("y",numberProductDone);
                stringObjectHashMap.put("value",productExpected);
                stringObjectHashMap.put("total",numberProduct);
                hashMaps.add(stringObjectHashMap);
            }
            return hashMaps;
        }catch (Exception e){
            throw new RuntimeException("Không lấy được dữ liệu hợp đồng");
        }
    }
    //************************************
    // Get top employee
    //************************************
    @Override
    public List<ReportTopEmployeeVO> getTopEmployee() {
        try{
            LocalDate dateNow = LocalDate.now();
            LocalDate dateStart = LocalDate.now();
            LocalDate dateEnd = LocalDate.now();
            if(dateNow.getDayOfMonth()<10){
                LocalDate dateTemp = dateNow.minusMonths(1);
                dateStart = LocalDate.of(dateTemp.getYear(),dateTemp.getMonthValue(),10);
                dateEnd = LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),10);
            }else{
                LocalDate dateTemp = dateNow.plusMonths(1);
                dateStart = LocalDate.of(dateNow.getYear(),dateNow.getMonthValue(),10);
                dateEnd = LocalDate.of(dateTemp.getYear(),dateTemp.getMonthValue(),10);
            }
            List<ReportTopEmployeeVO> getTopEmployee = productionOrderRepository.getTopEmployee(dateStart,dateEnd);
            return getTopEmployee;
        }catch (Exception e){
            throw new RuntimeException("Không lấy được dữ liệu hợp đồng");
        }
    }
}
