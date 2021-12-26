package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.AccountCustomRepository;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.util.AccountValidate.AddAccountValidate;
import com.university.fpt.acf.vo.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AccountManagerServiceImpl implements AccountManagerService {
    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private AccountCustomRepository accountCustomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender emailSender;

    //************************************
    // generate password with length 8 with numbers and lowercase letters
    //************************************
    private String generatePassword() {
        String result = "";
        result = RandomStringUtils.random(8, true, true);
        return result;
    }

    @Value( "${acf.scross.path}" )
    private String path;
    //************************************
    // Add account
    //************************************
    @Override
    public Boolean insertAccount(AddAccountForm addAccountForm) {
        Boolean insert = false;
        try {
            //check exit Employee
            String checkEmployeeExit = accountManagerRepository.checkEmplyeeInAccountExit(addAccountForm.getEmployee());
            // check username gender
            if (checkEmployeeExit == null || checkEmployeeExit.isEmpty()) {
                String generateUserByIdEmployee = this.GenerateUsername(addAccountForm.getEmployee());
                if (generateUserByIdEmployee.equals(addAccountForm.getUsername())) {
                    Account ac = new Account();
                    String password = generatePassword();
                    ac.setPassword(passwordEncoder.encode(password));
                    ac.setUsername(addAccountForm.getUsername());
                    AccountSercurity accountSercurity = new AccountSercurity();
                    ac.setCreated_by(accountSercurity.getUserName());
                    ac.setModified_by(accountSercurity.getUserName());
                    List<Role> listRole = new ArrayList<>();
                    for (Long i : addAccountForm.getListRole()) {
                        Role role = new Role();
                        role.setId(i);
                        listRole.add(role);
                    }
                    ac.setRoles(listRole);
                    Employee em = employeeRepository.getEmployeeToUpdateById(addAccountForm.getEmployee());
                    ac.setEmployee(em);
                    accountManagerRepository.save(ac);

                    // send mail

                    MimeMessage mimeMessage = emailSender.createMimeMessage();
                    MimeMessageHelper helper =
                            new MimeMessageHelper(mimeMessage, "utf-8");
                    helper.setText(this.buildEmail(addAccountForm.getUsername(), password, em.getFullName(),path+ "/#/login"), true);
                    helper.setTo(em.getEmail());
                    helper.setSubject("Kích hoạt tài khoản thành công trên hệ thống công ty ANH CHUNG FURNITURE");
                    emailSender.send(mimeMessage);


                    insert = true;
                } else {
                    throw new Exception("Username not correct");
                }
            } else {
                throw new Exception("Employee has account exit");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return insert;
    }
    //************************************
    // reset password cho account
    // 1. reset password
    // 2. send mail
    //************************************
    @Override
    public Boolean resetPassword(Long id) {
        Boolean check = false;
        try {
            Account ac = accountManagerRepository.findAccountById(id);
            String password = generatePassword();
            ac.setPassword(passwordEncoder.encode(password));
            AccountSercurity accountSercurity = new AccountSercurity();
            ac.setModified_date(LocalDate.now());
            ac.setModified_by(accountSercurity.getUserName());
            accountManagerRepository.save(ac);
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(this.buildEmailReset(ac.getUsername(), password, ac.getEmployee().getFullName(),path+ "/#/login"), true);
            helper.setTo(ac.getEmployee().getEmail());
            helper.setSubject("Cài lại mật khẩu tài khoản thành công trên hệ thống công ty ANH CHUNG FURNITURE");
            emailSender.send(mimeMessage);
            check = true;
        } catch (Exception ex) {
            throw new RuntimeException("Không thể cài lại mật khẩu");
        }
        return check;
    }
    //************************************
    // change password of account
    // 1. check password old
    // 2. change password
    //************************************
    @Override
    public Boolean changePassword(ChangePasswordAccountForm changePasswordAccountForm) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            Account ac = accountManagerRepository.findAccountByUsername(accountSercurity.getUserName());
            String passwordOld = ac.getPassword();
            if(passwordEncoder.matches(changePasswordAccountForm.getOldPassword(), passwordOld)){
                if(changePasswordAccountForm.getNewPassword().length() < 8){
                    return check;
                }
                ac.setPassword(passwordEncoder.encode(changePasswordAccountForm.getNewPassword()));
                ac.setModified_date(LocalDate.now());
                ac.setModified_by(accountSercurity.getUserName());
                accountManagerRepository.save(ac);
                check = true;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Không thể cài lại mật khẩu");
        }
        return check;
    }
    //************************************
    // build template mail reset pass
    //************************************
//    private String buildEmailReset(String username, String password, String name, String link) {
//        StringBuilder sql = new StringBuilder("");
//        sql.append("<div style=\" width:80%; margin: 0 auto;\">\n" +
//                "        <img src=\"\">\n" +
//                "        <table style=\"width:100%;\">\n" +
//                "            <tr>\n" +
//                "                <td colspan=\" 2 \">Xin chào " + name + ", </td>\n" +
//                "            </tr>\n" +
//                "            <tr>\n" +
//                "                <td>Chúc mừng bạn đã cài lại mật khẩu thành công</td>\n" +
//                "            </tr>\n" +
//                "            <tr>\n" +
//                "                <td style=\"padding-left: 30px;\">- Tài khoản: " + username + "</td>\n" +
//                "            </tr>\n" +
//                "            <tr>\n" +
//                "                <td style=\"padding-left: 30px;\">- Mật Khẩu mới: " + password + "</td>\n" +
//                "            </tr>\n" +
//                "        </table>\n" +
//                "        <br>\n" +
//                "        <p>vui lòng mời bạn nhấn vào đường dẫn bên dưới để đến với trang mạng của công ty. </p>\n" +
//                "        <button style=\"display: block; margin-left: auto; margin-right: auto; background-color: #40A9FF; color: white;\">" +
//                "      <a href=\"" + link + "\">Đăng nhập!</a></button>\n" +
//                "        <p>Trân trọng,</p>\n" +
//                "        <h3 style=\"font-family: 'Courier New', Courier, monospace \">Anh Chung Furniture</h3>\n" +
//                "    </div>");
//        return sql.toString();
//    }

    private String buildEmailReset(String username, String password, String name, String link) {
        StringBuilder sql = new StringBuilder("");
        sql.append("\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "    <title></title>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "    <div style=\"max-width: 680px; \">\n" +
                "        <table width=\"100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"background-color:#ffffff;padding:0 10px 10px;text-align:left\">\n" +
                "                    <table width=\"80%\">\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding: 10px;font-family: 'Arial', sans-serif;font-size:14px; line-height: 21px;color:#000000;\">\n" +
                "                                <p style=\"margin:0;\">\n" +
                "                                    <strong>\n" +
                "                                        Xin chào "+name+",\n" +
                "                                        <p style=\"color:#000000\">Chúc mừng bạn đã cài lại mật khẩu thành công</p>\n" +
                "                                        <p style=\"text-align:center; color:#000000\">Tài khoản: "+username+"</p>\n" +
                "                                        <p style=\"text-align:center; color:#000000\">Mật khẩu: "+password+"</p>\n" +
                "                                    </strong>\n" +
                "                                </p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 5px;background-color:#ffffff;\">\n" +
                "                    <table style=\"margin: auto;\">\n" +
                "                        <tr>\n" +
                "                            <p style=\"text-align:center;\">\n" +
                "                                <strong>\n" +
                "                                    <p style=\"color:#3c8491\">\n" +
                "                                        Vui lòng mời bạn nhấn vào nút \"Đăng nhập\" bên dưới để đến với phần mềm của\n" +
                "                                        công ty:\n" +
                "                                    </p>\n" +
                "                                </strong>\n" +
                "                            </p>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"border-radius: 30px; background: #00B5AD;text-align:center\">\n" +
                "                                <a href=\""+link+"\" style=\"border: 1px solid #00B5AD; font-family: Arial,sans-serif; font-size:14px;line-height: 14px; text-decoration: none; padding: 10px 31px; color: #ffffff; font-weight:bold;display: block; border-radius: 30px;\"><strong>\n" +
                "                                        Đăng nhập</strong></a>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div style=\"background-color:#ffffff;margin: 0 -1px; width:100%; max-width:680px; vertical-align:top;\" class=\"cc-column\">\n" +
                "                        <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding-top:0; padding-left: 10px; font-family: Arial, sans-serif; font-size:14px;color: #0C2340;\">\n" +
                "                                    Trân trọng,\n" +
                "                                    <h4> Công Ty Nội Thất Anh Chung</h4>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height=\"1\" style=\"line-height:2px; font-size:2px; background-color: #0C2340;\">\n" +
                "                                    &nbsp;\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; font-family: Arial, sans-serif; font-size:14px; color: #0C2340;background-color:#ffffff;\">\n" +
                "                    <table style=\" font-family: 'Oxygen', sans-serif; font-size: 14px;\">\n" +
                "                        <tr>\n" +
                "                            <td style=\"width: 50vh\">\n" +
                "                                <img src=\"https://i.postimg.cc/m25nfzcj/logo.jpg\" alt=\"\" style=\"width:250px;\">\n" +
                "                            </td>\n" +
                "                            <td style=\"width: 50vh\">\n" +
                "                                <h3>Công Ty Nội Thất Anh Chung </h3>\n" +
                "                                <p>\n" +
                "                                    Địa chỉ: Thôn 10, Kim Quan, Thạch Thất, Hà Nội\n" +
                "                                </p>\n" +
                "                                <p>\n" +
                "                                    Số điện thoại: 0975 446 588\n" +
                "                                </p>\n" +
                "                                <p>\n" +
                "                                    Email: levanchung1312@gmail.com\n" +
                "                                </p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>\n" +
                "</body>\n" +
                "\n" +
                "</html>");
        return sql.toString();
    }
    //************************************
    // build template mail create account
    //************************************
//    private String buildEmail(String username, String password, String name, String link) {
//        StringBuilder sql = new StringBuilder("");
//        sql.append("<div style=\" width:80%; margin: 0 auto;\">\n" +
//                "        <img src=\"\">\n" +
//                "        <table style=\"width:100%;\">\n" +
//                "            <tr>\n" +
//                "                <td colspan=\" 2 \">Xin chào " + name + ", </td>\n" +
//                "            </tr>\n" +
//                "            <tr>\n" +
//                "                <td>Chúc mừng bạn đã trở thành một nhân tố quan trọng của công ty ANH CHUNG FURNITURE</td>\n" +
//                "            </tr>\n" +
//                "            <tr>\n" +
//                "                <td style=\"padding-left: 30px;\">- Tài khoản: " + username + "</td>\n" +
//                "            </tr>\n" +
//                "            <tr>\n" +
//                "                <td style=\"padding-left: 30px;\">- Mật Khẩu: " + password + "</td>\n" +
//                "            </tr>\n" +
//                "        </table>\n" +
//                "        <br>\n" +
//                "        <p>vui lòng mời bạn nhấn vào đường dẫn bên dưới để đến với trang mạng của công ty. </p>\n" +
//                "        <button style=\"display: block; margin-left: auto; margin-right: auto; background-color: #40A9FF; color: white;\">" +
//                "      <a href=\"" + link + "\">Đăng nhập!</a></button>\n" +
//                "        <p>Trân trọng,</p>\n" +
//                "        <h3 style=\"font-family: 'Courier New', Courier, monospace \">Anh Chung Furniture</h3>\n" +
//                "    </div>");
//        return sql.toString();
//    }

    private String buildEmail(String username, String password, String name, String link) {
        StringBuilder sql = new StringBuilder("");
        sql.append("    <div style=\"max-width: 680px; \">\n" +
                "        <table width=\"100%\">\n" +
                "            <tr>\n" +
                "                <td style=\"background-color:#ffffff;padding:0 10px 10px;text-align:left\">\n" +
                "                    <table width=\"100%\">\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding: 10px;font-family: 'Arial', sans-serif;font-size:14px; line-height: 21px;color:#000000;\">\n" +
                "                                <p style=\"margin:0;\">\n" +
                "                                    <strong>\n" +
                "                                        Xin chào "+name+",\n" +
                "                                        <p style=\"color:#000000\">Chúc mừng bạn đã trở thành một nhân tố quan trọng của công ty ANH CHUNG FURNITURE</p>\n" +
                "                                        <p style=\"text-align:center; color:#000000\">Tài khoản: "+username+"</p>\n" +
                "                                        <p style=\"text-align:center; color:#000000\">Mật khẩu: "+password+"</p>\n" +
                "                                    </strong>\n" +
                "                                </p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 5px;background-color:#ffffff;\">\n" +
                "                    <table style=\"margin: auto;\">\n" +
                "                        <tr>\n" +
                "                            <p style=\"text-align:center;\">\n" +
                "                                <strong>\n" +
                "                                    <p style=\"color:#3c8491\">\n" +
                "                                        Vui lòng mời bạn nhấn vào nút \"Đăng nhập\" bên dưới để đến với phần mềm của\n" +
                "                                        công ty:\n" +
                "                                    </p>\n" +
                "                                </strong>\n" +
                "                            </p>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"border-radius: 30px; background: #00B5AD;text-align:center\">\n" +
                "                                <a href=\""+link+"\" style=\"border: 1px solid #00B5AD; font-family: Arial,sans-serif; font-size:14px;line-height: 14px; text-decoration: none; padding: 10px 31px; color: #ffffff; font-weight:bold;display: block; border-radius: 30px;\"><strong>\n" +
                "                                        Đăng nhập</strong></a>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "                    <div style=\"background-color:#ffffff;margin: 0 -1px; width:100%; max-width:680px; vertical-align:top;\" class=\"cc-column\">\n" +
                "                        <table cellspacing=\"0\" cellpadding=\"0\" width=\"100%\">\n" +
                "                            <tr>\n" +
                "                                <td style=\"padding-top:0; padding-left: 10px; font-family: Arial, sans-serif; font-size:14px;color: #0C2340;\">\n" +
                "                                    Trân trọng,\n" +
                "                                    <h4> Công Ty Nội Thất Anh Chung</h4>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr>\n" +
                "                                <td height=\"1\" style=\"line-height:2px; font-size:2px; background-color: #0C2340;\">\n" +
                "                                    &nbsp;\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding: 10px; font-family: Arial, sans-serif; font-size:14px; color: #0C2340;background-color:#ffffff;\">\n" +
                "                    <table style=\" font-family: 'Oxygen', sans-serif; font-size: 14px;\">\n" +
                "                        <tr>\n" +
                "                            <td style=\"width: 50vh\">\n" +
                "                                <img src=\"https://i.postimg.cc/m25nfzcj/logo.jpg\" alt=\"\" style=\"width:250px;\">\n" +
                "                            </td>\n" +
                "                            <td style=\"width: 50vh\">\n" +
                "                                <h3>Công Ty Nội Thất Anh Chung </h3>\n" +
                "                                <p>\n" +
                "                                    Địa chỉ: Thôn 10, Kim Quan, Thạch Thất, Hà Nội\n" +
                "                                </p>\n" +
                "                                <p>\n" +
                "                                    Số điện thoại: 0975 446 588\n" +
                "                                </p>\n" +
                "                                <p>\n" +
                "                                    Email: levanchung1312@gmail.com\n" +
                "                                </p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                    </table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "    </div>");
        return sql.toString();
    }
    //************************************
    // Update account
    //************************************
    @Override
    public Boolean updateAccount(UpdateAccountForm updateAccountForm) {
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            Account ac = accountManagerRepository.findAccountById(updateAccountForm.getId());
            if (ac.getUsername().equals(accountSercurity.getUserName())) {
                throw new Exception("Account is available");
            }
            if (ac != null) {
                ac.setStatus(updateAccountForm.getStatus());
                ac.setModified_by(accountSercurity.getUserName());
                ac.setModified_date(LocalDate.now());
                ac.setModified_by(accountSercurity.getUserName());
                List<Role> listRole = new ArrayList<>();
                for (Long i : updateAccountForm.getListRole()) {
                    Role role = new Role();
                    role.setId(i);
                    listRole.add(role);
                }
                ac.setRoles(listRole);
                accountManagerRepository.save(ac);
                return true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    //************************************
    //  delete account by id
    //************************************
    @Override
    public Boolean deleteAccount(Long idAccount) {
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            Account account = accountManagerRepository.findAccountById(idAccount);
            if (account.getUsername().equals(accountSercurity.getUserName())) {
                throw new Exception("Account is available");
            }
            account.setModified_by(accountSercurity.getUserName());
            account.setModified_date(LocalDate.now());
            account.setDeleted(true);
            accountManagerRepository.save(account);
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }
    //************************************
    // Search account  with combination of fields: name, role, status, date
    //************************************
    @Override
    public List<GetAllAccountResponseVO> searchAccount(SearchAccountForm searchAccountForm) {
        List<GetAllAccountVO> listAcc = accountCustomRepository.getAllAccount(searchAccountForm);
        List<GetAllAccountResponseVO> result = new ArrayList<>();
        GetAllAccountResponseVO accountResponseVO = new GetAllAccountResponseVO();
        for (int i = 0; i < listAcc.size(); i++) {
            if (!listAcc.get(i).getId().equals(accountResponseVO.getId())) {
                if (i != 0) {
                    result.add(accountResponseVO);
                }
                accountResponseVO = new GetAllAccountResponseVO();
                accountResponseVO.setId(listAcc.get(i).getId());
                accountResponseVO.setStatus(listAcc.get(i).getStatus());
                accountResponseVO.setTime(listAcc.get(i).getTime());
                accountResponseVO.setUsername(listAcc.get(i).getUsername());
                if (listAcc.get(i).getIdRole() != null) {
                    accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(), listAcc.get(i).getNameRole()));
                }
            } else {
                if (listAcc.get(i).getIdRole() != null) {
                    accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(), listAcc.get(i).getNameRole()));
                }
            }
        }
        if (!accountResponseVO.getRoles().isEmpty()) {
            result.add(accountResponseVO);
        }
        return result;
    }
    //************************************
    // Get total search account
    //************************************
    @Override
    public int getTotalSearchAccount(SearchAccountForm searchAccountForm) {
        if (searchAccountForm.getTotal() != null && searchAccountForm.getTotal().intValue() != 0) {
            return searchAccountForm.getTotal();
        }
        return accountCustomRepository.getTotalAllAccount(searchAccountForm);
    }
    //************************************
    // Get detail account by id
    //************************************
    @Override
    public GetAccountDetailResponeVO getAccountById(Long id) {
        try {
            List<GetAccountDetailVO> ac = accountManagerRepository.getAccountById(id);
            GetAccountDetailResponeVO result = new GetAccountDetailResponeVO();
            List<RoleAccountVO> listRole = new ArrayList<>();
            for (GetAccountDetailVO acd : ac) {
                RoleAccountVO role = new RoleAccountVO();
                role.setId(acd.getIdRole());
                role.setName(acd.getNameRole());
                listRole.add(role);
            }
            result.setId(ac.get(0).getId());
            result.setUsername(ac.get(0).getUsername());
            result.setFullname(ac.get(0).getFullname());
            result.setImage(ac.get(0).getImage());
            result.setGender(ac.get(0).getGender());
            result.setDob(ac.get(0).getDob());
            result.setPhone(ac.get(0).getPhone());
            result.setRoles(listRole);
            result.setEmail(ac.get(0).getEmail());
            result.setAddress(ac.get(0).getAddress());
            return result;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;

    }
    //************************************
    // Generate username by id employee
    //************************************
    @Override
    public String GenerateUsername(Long id) {
        try {
            String fullname = employeeRepository.getFullNameById(id);
            if (fullname != null && !fullname.isEmpty()) {
                AddAccountValidate addAccountValidate = new AddAccountValidate();
                String usernameGen = addAccountValidate.generateFormatUsernameByFullname(fullname);
                List<String> list = accountManagerRepository.getAllUsernameIsLike(usernameGen);
                Integer number = addAccountValidate.genNumberUsername(usernameGen, list);
                return usernameGen + ((number == 0) ? "" : number);
            }
        } catch (Exception e) {
            throw new RuntimeException("Username not correct!");
        }
        return null;
    }


}
