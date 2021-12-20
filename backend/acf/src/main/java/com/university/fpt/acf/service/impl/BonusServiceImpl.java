package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.BonusService;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class BonusServiceImpl implements BonusService {
    @Autowired
    private BonusRepository bonusRepository;
    @Autowired
    private BonusCustomRepository bonusCustomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private HistorySalaryRepository historySalaryRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AccountManagerRepository accountManagerRepository;
    //************************************
    // Search bonus  with combination of fields: title, date, status
    //************************************
    @Override
    public List<ResultSearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchBonus) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        List<ResultSearchBonusAdminVO> listResult = new ArrayList<>();
        int dem = 0;
        try {
            list = bonusCustomRepository.searchBonus(searchBonus);
            ResultSearchBonusAdminVO re = new ResultSearchBonusAdminVO();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    re.setId(list.get(i).getId());
                    re.setMoney(list.get(i).getMoney());
                    re.setReason(list.get(i).getReason());
                    re.setTitle(list.get(i).getTitle());
                    re.setEffectiveDate(list.get(i).getEffectiveDate());
                    re.setStatus(list.get(i).getStatus());
                    GetAllEmployeeVO em = new GetAllEmployeeVO();
                    em.setId(list.get(i).getIdEmployee());
                    em.setName(list.get(i).getNameEmployee());
                    re.getListIdEmployee().add(em);
                    listResult.add(re);
                } else {
                    if (listResult.get(listResult.size() - 1).getId().equals(list.get(i).getId())) {
                        GetAllEmployeeVO em = new GetAllEmployeeVO();
                        em.setId(list.get(i).getIdEmployee());
                        em.setName(list.get(i).getNameEmployee());
                        listResult.get(listResult.size() - 1).getListIdEmployee().add(em);
                    } else {
                        re = new ResultSearchBonusAdminVO();
                        re.setId(list.get(i).getId());
                        re.setMoney(list.get(i).getMoney());
                        re.setReason(list.get(i).getReason());
                        re.setTitle(list.get(i).getTitle());
                        re.setEffectiveDate(list.get(i).getEffectiveDate());
                        re.setStatus(list.get(i).getStatus());
                        GetAllEmployeeVO em = new GetAllEmployeeVO();
                        em.setId(list.get(i).getIdEmployee());
                        em.setName(list.get(i).getNameEmployee());
                        re.getListIdEmployee().add(em);
                        listResult.add(re);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return listResult;
    }
    //************************************
    // Get total search bunus
    //************************************
    @Override
    public int totalSearchBonus(SearchBonusAdminForm searchBonus) {
        int size;
        try {
            size = bonusCustomRepository.totalSearchBonus(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }
    //************************************
    // Search Bunus and punish  with combination of fields: title, date, status,bonus
    //************************************
    @Override
    public List<SearchBonusAndPunishVO> searchBonusAndPunish(SearchBonusAndPunishForm searchBonusAndPunishForm) {
        List<SearchBonusAndPunishVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = bonusCustomRepository.searchBonusAndPunish(accountSercurity.getUserName(), searchBonusAndPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return list;
    }
    //************************************
    // Get total search bonus and punish
    //************************************
    @Override
    public int totalSearchBonusAndPunish(SearchBonusAndPunishForm searchBonusAndPunishForm) {
        int size;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            size = bonusCustomRepository.totalSearchBonusAndPunish(accountSercurity.getUserName(), searchBonusAndPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }
    //************************************
    // Search Bonus user
    //************************************
    @Override
    public List<SearchBonusAdminVO> searchBonusUser(BonusPunishForm bonusPunishForm) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = bonusCustomRepository.searchBonusUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return list;
    }
    //************************************
    // Get total search bonus user
    //************************************
    @Override
    public int totalSearchBonusUser(BonusPunishForm bonusPunishForm) {
        if (bonusPunishForm.getTotal() != null && bonusPunishForm.getTotal() != 0) {
            return bonusPunishForm.getTotal().intValue();
        }
        int size;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            size = bonusCustomRepository.totalSearchBonusUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }

    //************************************
    // Add bonus
    //************************************
    @Override
    @Transactional
    public Boolean addBonus(AddBonusAdminForm addBonus) {
        boolean check = false;
        try {
            BonusPenalty bonus = new BonusPenalty();
            bonus.setBonus(true);
            bonus.setTitle(addBonus.getTitle());
            bonus.setMoney(addBonus.getMoney());
            bonus.setReason(addBonus.getReason());
            bonus.setStatus(addBonus.getStatus());
            bonus.setEffectiveDate(addBonus.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(addBonus.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setModified_by(accountSercurity.getUserName());
            bonusRepository.saveAndFlush(bonus);
            check = true;

            if (addBonus.getStatus()) {

                List<String> usernames = accountManagerRepository.getUsernameByIdEmployee(addBonus.getListIdEmployee());
                for(String s : usernames){
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" tạo một đơn khen thưởng cho bạn");
                    notification.setPath("/viewbonuspunish");
                    HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
                }

                LocalDate date = LocalDate.now();
                int day = date.getDayOfMonth();
                if (day < 10) {
                    LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    date = date.minusMonths(1);
                    LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);
                    if (dateStart.isBefore(addBonus.getEffectiveDate()) && dateEnd.isAfter(addBonus.getEffectiveDate())) {
                        for (Long idEmpl : addBonus.getListIdEmployee()) {
                            HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl, LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                            Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                            bonusMoney += Integer.valueOf(addBonus.getMoney());
                            historySalary.setBonus(bonusMoney + "");
                            historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(addBonus.getMoney())) + "");
                            historySalary.setModified_by(accountSercurity.getUserName());
                            historySalary.setModified_date(LocalDate.now());
                            historySalaryRepository.save(historySalary);
                        }
                    }
                } else {
                    LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);
                    date = date.plusMonths(1);
                    LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    if (dateStart.isBefore(addBonus.getEffectiveDate()) && dateEnd.isAfter(addBonus.getEffectiveDate())) {
                        for (Long idEmpl : addBonus.getListIdEmployee()) {
                            HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl, LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                            Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                            bonusMoney += Integer.valueOf(addBonus.getMoney());
                            historySalary.setBonus(bonusMoney + "");
                            historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(addBonus.getMoney())) + "");
                            historySalary.setModified_by(accountSercurity.getUserName());
                            historySalary.setModified_date(LocalDate.now());
                            historySalaryRepository.save(historySalary);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Delete bonus
    //************************************
    @Override
    public Boolean deleteBonus(Long id) {
        boolean check = false;
        try {
            BonusPenalty bonus = bonusRepository.getBonusById(id);

            if(bonus.getStatus()){
                throw new RuntimeException("Quyết định khen thưởng có hiệu lực không được xóa");
            }
            AccountSercurity accountSercurity = new AccountSercurity();


            if (!bonus.getStatus()) {
                bonus.setDeleted(true);
                bonus.setModified_by(accountSercurity.getUserName());
                bonus.setModified_date(LocalDate.now());
                bonusRepository.save(bonus);
                check = true;
            } else {

                List<Long> isEmployees = new ArrayList<>();
                for (Employee employee : bonus.getEmployees()) {
                    isEmployees.add(employee.getId());
                }

                List<String> usernames = accountManagerRepository.getUsernameByIdEmployee(isEmployees);
                for (String s : usernames) {
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" đã xóa đơn khen thưởng cho bạn");
                    notification.setPath("/viewbonuspunish");
                    HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
                }

                LocalDate date = LocalDate.now();
                int day = date.getDayOfMonth();
                if (day < 10) {
                    LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    date = date.minusMonths(1);
                    LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);
                    if (dateStart.isBefore(bonus.getEffectiveDate())) {
                        bonus.setDeleted(true);
                        bonus.setModified_by(accountSercurity.getUserName());
                        bonus.setModified_date(LocalDate.now());
                        bonusRepository.save(bonus);
                        check = true;


                        // update salary

                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                bonusMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setBonus(bonusMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    }
                } else {
                    LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);
                    date = date.plusMonths(1);
                    LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    if (dateStart.isBefore(bonus.getEffectiveDate())) {
                        bonus.setDeleted(true);
                        bonus.setModified_by(accountSercurity.getUserName());
                        bonus.setModified_date(LocalDate.now());
                        bonusRepository.save(bonus);
                        check = true;

                        // update salary

                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setBonus(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Update bonus
    //************************************
    @Override
    public Boolean updateBonus(UpdateBonusForm updateBonus) {
        boolean check = false;
        try {
            BonusPenalty bonus = bonusRepository.getBonusById(updateBonus.getId());
            if(bonus.getStatus()){
                throw new RuntimeException("Quyết định khen thưởng có hiệu lực không được sửa");
            }
            AccountSercurity accountSercurity = new AccountSercurity();

            if(updateBonus.getStatus()){

                List<Long> isEmployees = new ArrayList<>();
                for (Employee employee : bonus.getEmployees()) {
                    isEmployees.add(employee.getId());
                }

                List<String> usernames = accountManagerRepository.getUsernameByIdEmployee(isEmployees);
                for (String s : usernames) {
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" cập nhật lại đơn khen thưởng cho bạn");
                    notification.setPath("/viewbonuspunish");
                    HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
                }
            }

            LocalDate date = LocalDate.now();
            int day = date.getDayOfMonth();

            if (day < 10) {
                LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                date = date.minusMonths(1);
                LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);

                if (!bonus.getStatus()) {
                    if (updateBonus.getStatus()) {
                        if (dateStart.isBefore(updateBonus.getEffectiveDate()) && dateEnd.isAfter(updateBonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                bonusMoney += Integer.valueOf(updateBonus.getMoney());
                                historySalary.setBonus(bonusMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(updateBonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    }
                } else {
                    if (!updateBonus.getStatus()) {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                bonusMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setBonus(bonusMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    } else {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            if (dateStart.isBefore(updateBonus.getEffectiveDate()) && dateEnd.isAfter(updateBonus.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                    Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                    bonusMoney -= Integer.valueOf(bonus.getMoney());
                                    bonusMoney += Integer.valueOf(updateBonus.getMoney());
                                    historySalary.setBonus(bonusMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney()) + Integer.valueOf(updateBonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            } else {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                    Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                    bonusMoney -= Integer.valueOf(bonus.getMoney());
                                    historySalary.setBonus(bonusMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        } else {
                            if (dateStart.isBefore(updateBonus.getEffectiveDate()) && dateEnd.isAfter(updateBonus.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                    Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                    bonusMoney += Integer.valueOf(updateBonus.getMoney());
                                    historySalary.setBonus(bonusMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(updateBonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        }
                    }
                }

            } else {
                LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);
                date = date.plusMonths(1);
                LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);

                if (!bonus.getStatus()) {
                    if (updateBonus.getStatus()) {
                        if (dateStart.isBefore(updateBonus.getEffectiveDate()) && dateEnd.isAfter(updateBonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                bonusMoney += Integer.valueOf(updateBonus.getMoney());
                                historySalary.setBonus(bonusMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(updateBonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    }
                } else {
                    if (!updateBonus.getStatus()) {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                bonusMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setBonus(bonusMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    } else {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            if (dateStart.isBefore(updateBonus.getEffectiveDate()) && dateEnd.isAfter(updateBonus.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                    Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                    bonusMoney -= Integer.valueOf(bonus.getMoney());
                                    bonusMoney += Integer.valueOf(updateBonus.getMoney());
                                    historySalary.setBonus(bonusMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney()) + Integer.valueOf(updateBonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            } else {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                    Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                    bonusMoney -= Integer.valueOf(bonus.getMoney());
                                    historySalary.setBonus(bonusMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(bonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        } else {
                            if (dateStart.isBefore(updateBonus.getEffectiveDate()) && dateEnd.isAfter(updateBonus.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                    Integer bonusMoney = Integer.parseInt(historySalary.getBonus());
                                    bonusMoney += Integer.valueOf(updateBonus.getMoney());
                                    historySalary.setBonus(bonusMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(updateBonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        }
                    }
                }
            }

            bonus.setBonus(true);
            bonus.setTitle(updateBonus.getTitle());
            bonus.setMoney(updateBonus.getMoney());
            bonus.setReason(updateBonus.getReason());
            bonus.setStatus(updateBonus.getStatus());
            bonus.setEffectiveDate(updateBonus.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(updateBonus.getListIdEmployee()));
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setModified_by(accountSercurity.getUserName());
            bonusRepository.saveAndFlush(bonus);
            check = true;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

}
