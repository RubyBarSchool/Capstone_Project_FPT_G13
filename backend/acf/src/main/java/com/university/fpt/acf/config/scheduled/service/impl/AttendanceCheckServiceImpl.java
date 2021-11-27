package com.university.fpt.acf.config.scheduled.service.impl;

import com.university.fpt.acf.config.scheduled.service.AttendanceCheckService;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.AttendanceRepository;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AttendanceCheckServiceImpl implements AttendanceCheckService {
    @Autowired
    private EmployeeCustomRepository employeeCustomRepository;

    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public void checkAttendance() {
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            int count = 0;
            if (dateTime.isAfter(LocalDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), 18, 40))) {
                count = 3;
            } else if (dateTime.isAfter(LocalDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), 18, 25))) {
                count = 2;
            } else if(dateTime.isAfter(LocalDateTime.of(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), 18, 10))){
                count = 1;
            }
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            String dateTimeAfterFormat = dateTime.format(dateTimeFormat);

            List<GetAllEmployeeVO> getAllEmployeeVOList = employeeCustomRepository.getAllEmployeeNotAttendanceJob();

            if (getAllEmployeeVOList != null && getAllEmployeeVOList.size() != 0) {
                List<String> listEmail = accountManagerRepository.getEmailAdmin();

                for (String s : listEmail) {
                    MimeMessage mimeMessage = emailSender.createMimeMessage();
                    MimeMessageHelper helper =
                            new MimeMessageHelper(mimeMessage, "utf-8");
                    helper.setText(this.buildEmail(getAllEmployeeVOList, "http://acf-client.s3-website.us-east-2.amazonaws.com/#/attendance"), true);
                    helper.setTo(s);
                    helper.setSubject((count==0?"":"[Nhắc lại lần " + count + "]")+ "Thư nhắc nhở chấm công ngày " + dateTimeAfterFormat);
                    emailSender.send(mimeMessage);
                }
            }
        } catch (MessagingException e) {
            throw new IllegalStateException("failed to send email");
        }
    }

    @Override
    public void autoAttendance() {
        try {
            List<GetAllEmployeeVO> getAllEmployeeVOList = employeeCustomRepository.getAllEmployeeNotAttendanceJob();
            List<TimeKeep> timeKeeps = new ArrayList<>();
            for (GetAllEmployeeVO getAllEmployeeVO : getAllEmployeeVOList) {
                TimeKeep timeKeep = new TimeKeep();
                timeKeep.setCreated_by("JOB_AUTO");
                timeKeep.setModified_by("JOB_AUTO");
                timeKeep.setDate(LocalDate.now());
                timeKeep.setType("0");
                timeKeep.setNote("Điểm danh tự động");
                Employee employee = new Employee();
                employee.setId(getAllEmployeeVO.getId());
                timeKeep.setEmployee(employee);
                timeKeeps.add(timeKeep);
            }
            attendanceRepository.saveAll(timeKeeps);
        } catch (Exception ex) {
            throw new IllegalStateException("Không thể chạy tự động");
        }

    }

    private String buildEmail(List<GetAllEmployeeVO> getAllEmployeeVOList, String link) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

        String dateTimeAfterFormat = dateTime.format(dateTimeFormat);

        StringBuilder sql = new StringBuilder("");
        sql.append("<div style=\" width:80%; margin: 0 auto;\">\n" +
                "        <img src=\"\">\n" +
                "        <table style=\"width:100%;\">\n" +
                "            <tr>\n" +
                "                <td colspan=\" 2 \">Xin chào, </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td>Hiện tại hệ thống có kiểm tra phần chấm công muốn thông báo đến bạn một số thông tin:</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding-left: 30px;\">- Thời gian: " + dateTimeAfterFormat + "</td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td style=\"padding-left: 30px;\">- Hệ thống có: " + getAllEmployeeVOList.size() + " người chưa được chấm công</td>\n" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <br>\n" +
                "        <table style=\" width:100%; border:1px solid black; margin: 0 auto;\">\n" +
                "<tr>\n" +
                "                <th style=\"border:1px solid black; \">Số thứ tự</th>\n" +
                "                <th style=\"border:1px solid black; \">Họ và tên</th>\n" +
                "                <th style=\"border:1px solid black; \">Trạng thái</th>\n" +
                "            </tr>\n");

        for (GetAllEmployeeVO getAllEmployeeVO : getAllEmployeeVOList) {
            sql.append("            <tr>\n" +
                    "                <td style=\"border:1px solid black; \">" + getAllEmployeeVO.getId() + "</td>\n" +
                    "                <td style=\"border:1px solid black; \">" + getAllEmployeeVO.getName() + "</td>\n" +
                    "                <td style=\"border:1px solid black; \">Chưa chấm công</td>\n" +
                    "            </tr>\n");
        }
        sql.append("        </table>\n" +
                "\n" +
                "        <br>\n" +
                "        <p>Đề nghị bạn ấn vào link bên dưới để <strong style=\"color: red; font-size: 25px; \">chấm công ngay</strong> cho các nhân viên đó.</p>\n" +
                "        <button style=\"display: block; margin-left: auto; margin-right: auto; background-color: #40A9FF; color: white;\">" +
                "      <a href=\"" + link + "\">Chấm công!</a></button>\n" +
                "        <p>Trân trọng,</p>\n" +
                "        <h3 style=\"font-family: 'Courier New', Courier, monospace \">Anh Chung Furniture</h3>\n" +
                "    </div>");
        return sql.toString();
    }
}
