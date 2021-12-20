package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.BonusPenalty;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.UpdateBonusForm;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.EmployeeService;
import com.university.fpt.acf.service.PunishService;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import com.university.fpt.acf.vo.ResultSearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class PunishServiceImpl implements PunishService {
    @Autowired
    private PunishRepository punishRepository;
    @Autowired
    private PunishCustomRepository punishCustomRepository;
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
    // Search punish  with combination of fields: codeMaterial, frame,group, unit, company
    //************************************
    @Override
    public List<ResultSearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchBonus) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        List<ResultSearchBonusAdminVO> listResult = new ArrayList<>();
        int dem = 0;
        try {
            list = punishCustomRepository.searchPunish(searchBonus);
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
            throw new RuntimeException("Error punish repository " + e.getMessage());
        }
        return listResult;
    }

    @Override
    public int totalSearchPunish(SearchBonusAdminForm searchBonus) {
        int size;
        try {
            size = punishCustomRepository.totalSearchPunish(searchBonus);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }
    //************************************
    // Search punish user
    //************************************
    @Override
    public List<SearchBonusAdminVO> searchPunishUser(BonusPunishForm bonusPunishForm) {
        List<SearchBonusAdminVO> list = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            list = punishCustomRepository.searchPunishUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus repository " + e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchPunishUser(BonusPunishForm bonusPunishForm) {
        if (bonusPunishForm.getTotal() != null && bonusPunishForm.getTotal() != 0) {
            return bonusPunishForm.getTotal().intValue();
        }
        int size;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            size = punishCustomRepository.totalSearchPunishUser(accountSercurity.getUserName(), bonusPunishForm);
        } catch (Exception e) {
            throw new RuntimeException("Error bonus  repository " + e.getMessage());
        }
        return size;
    }
    //************************************
    // Add punish
    //************************************
    @Override
    @Transactional
    public Boolean addPunish(AddBonusAdminForm addPunish) {
        boolean check = false;
        try {
            BonusPenalty punish = new BonusPenalty();
            punish.setBonus(false);
            punish.setTitle(addPunish.getTitle());
            punish.setMoney(addPunish.getMoney());
            punish.setReason(addPunish.getReason());
            punish.setStatus(addPunish.getStatus());
            punish.setEffectiveDate(addPunish.getEffectiveDate());
            punish.setEmployees(employeeRepository.getEmployeeByIdS(addPunish.getListIdEmployee()));
            AccountSercurity accountSercurity = new AccountSercurity();
            punish.setCreated_by(accountSercurity.getUserName());
            punish.setModified_by(accountSercurity.getUserName());
            punishRepository.saveAndFlush(punish);

            if (addPunish.getStatus()) {

                List<String> usernames = accountManagerRepository.getUsernameByIdEmployee(addPunish.getListIdEmployee());
                for (String s : usernames) {
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" tạo một đơn kỉ luật cho bạn");
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
                    if (dateStart.isBefore(addPunish.getEffectiveDate()) && dateEnd.isAfter(addPunish.getEffectiveDate())) {
                        for (Long idEmpl : addPunish.getListIdEmployee()) {
                            HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl, LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                            Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                            punishMoney += Integer.valueOf(addPunish.getMoney());
                            historySalary.setPenalty(punishMoney + "");
                            historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(addPunish.getMoney())) + "");
                            historySalary.setModified_by(accountSercurity.getUserName());
                            historySalary.setModified_date(LocalDate.now());
                            historySalaryRepository.save(historySalary);
                        }
                    }
                } else {
                    LocalDate dateStart = LocalDate.of(date.getYear(), date.getMonthValue(), 9);
                    date = date.plusMonths(1);
                    LocalDate dateEnd = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    if (dateStart.isBefore(addPunish.getEffectiveDate()) && dateEnd.isAfter(addPunish.getEffectiveDate())) {
                        for (Long idEmpl : addPunish.getListIdEmployee()) {
                            HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl, LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                            Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                            punishMoney += Integer.valueOf(addPunish.getMoney());
                            historySalary.setPenalty(punishMoney + "");
                            historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(addPunish.getMoney())) + "");
                            historySalary.setModified_by(accountSercurity.getUserName());
                            historySalary.setModified_date(LocalDate.now());
                            historySalaryRepository.save(historySalary);
                        }
                    }
                }
            }


            check = true;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    // Delete punish
    //************************************
    @Override
    public Boolean deletePunish(Long id) {
        boolean check = false;
        try {
            BonusPenalty bonus = punishRepository.getPunishById(id);
            if(bonus.getStatus()){
                throw new RuntimeException("Quyết định kỷ luật có hiệu lực không được xóa");
            }
            AccountSercurity accountSercurity = new AccountSercurity();
            if (!bonus.getStatus()) {
                bonus.setDeleted(true);
                bonus.setModified_by(accountSercurity.getUserName());
                bonus.setModified_date(LocalDate.now());
                punishRepository.save(bonus);
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
                    notification.setContent(" đã xóa đơn kỉ luật cho bạn");
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
                        punishRepository.save(bonus);
                        check = true;

                        // update salary

                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setPenalty(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney())) + "");
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
                        punishRepository.save(bonus);
                        check = true;

                        // update salary

                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setPenalty(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney())) + "");
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

    @Override
    public Boolean updatePunish(UpdateBonusForm updateForm) {
        boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            BonusPenalty bonus = punishRepository.getPunishById(updateForm.getId());
            if(bonus.getStatus()){
                throw new RuntimeException("Quyết định kỷ luật có hiệu lực không được sửa");
            }
            if(updateForm.getStatus()){

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
                    notification.setContent(" cập nhật lại đơn kỉ luật cho bạn");
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
                    if (updateForm.getStatus()) {
                        if (dateStart.isBefore(updateForm.getEffectiveDate()) && dateEnd.isAfter(updateForm.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney += Integer.valueOf(updateForm.getMoney());
                                historySalary.setPenalty(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(updateForm.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    }
                } else {
                    if (!updateForm.getStatus()) {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setPenalty(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    } else {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            if (dateStart.isBefore(updateForm.getEffectiveDate()) && dateEnd.isAfter(updateForm.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                    Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                    punishMoney -= Integer.valueOf(bonus.getMoney());
                                    punishMoney += Integer.valueOf(updateForm.getMoney());
                                    historySalary.setPenalty(punishMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney()) - Integer.valueOf(updateForm.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            } else {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                    Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                    punishMoney -= Integer.valueOf(bonus.getMoney());
                                    historySalary.setPenalty(punishMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        } else {
                            if (dateStart.isBefore(updateForm.getEffectiveDate()) && dateEnd.isAfter(updateForm.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(date.getYear(), date.getMonthValue(), 10));
                                    Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                    punishMoney += Integer.valueOf(updateForm.getMoney());
                                    historySalary.setPenalty(punishMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(updateForm.getMoney())) + "");
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
                    if (updateForm.getStatus()) {
                        if (dateStart.isBefore(updateForm.getEffectiveDate()) && dateEnd.isAfter(updateForm.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney += Integer.valueOf(updateForm.getMoney());
                                historySalary.setPenalty(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(updateForm.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    }
                } else {
                    if (!updateForm.getStatus()) {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            for (Employee idEmpl : bonus.getEmployees()) {
                                HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                punishMoney -= Integer.valueOf(bonus.getMoney());
                                historySalary.setPenalty(punishMoney + "");
                                historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney())) + "");
                                historySalary.setModified_by(accountSercurity.getUserName());
                                historySalary.setModified_date(LocalDate.now());
                                historySalaryRepository.save(historySalary);
                            }
                        }
                    } else {
                        if (dateStart.isBefore(bonus.getEffectiveDate()) && dateEnd.isAfter(bonus.getEffectiveDate())) {
                            if (dateStart.isBefore(updateForm.getEffectiveDate()) && dateEnd.isAfter(updateForm.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                    Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                    punishMoney -= Integer.valueOf(bonus.getMoney());
                                    punishMoney += Integer.valueOf(updateForm.getMoney());
                                    historySalary.setPenalty(punishMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney()) - Integer.valueOf(updateForm.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            } else {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                    Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                    punishMoney -= Integer.valueOf(bonus.getMoney());
                                    historySalary.setPenalty(punishMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) + Integer.valueOf(bonus.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        } else {
                            if (dateStart.isBefore(updateForm.getEffectiveDate()) && dateEnd.isAfter(updateForm.getEffectiveDate())) {
                                for (Employee idEmpl : bonus.getEmployees()) {
                                    HistorySalary historySalary = historySalaryRepository.getSalaryByEmployee(idEmpl.getId(), LocalDate.of(dateStart.getYear(), dateStart.getMonthValue(), 10));
                                    Integer punishMoney = Integer.parseInt(historySalary.getPenalty());
                                    punishMoney += Integer.valueOf(updateForm.getMoney());
                                    historySalary.setPenalty(punishMoney + "");
                                    historySalary.setTotalMoney((Integer.parseInt(historySalary.getTotalMoney()) - Integer.valueOf(updateForm.getMoney())) + "");
                                    historySalary.setModified_by(accountSercurity.getUserName());
                                    historySalary.setModified_date(LocalDate.now());
                                    historySalaryRepository.save(historySalary);
                                }
                            }
                        }
                    }
                }
            }

            bonus.setBonus(false);
            bonus.setTitle(updateForm.getTitle());
            bonus.setMoney(updateForm.getMoney());
            bonus.setReason(updateForm.getReason());
            bonus.setStatus(updateForm.getStatus());
            bonus.setEffectiveDate(updateForm.getEffectiveDate());
            bonus.setEmployees(employeeRepository.getEmployeeByIdS(updateForm.getListIdEmployee()));
            bonus.setCreated_by(accountSercurity.getUserName());
            bonus.setModified_by(accountSercurity.getUserName());
            punishRepository.saveAndFlush(bonus);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}
