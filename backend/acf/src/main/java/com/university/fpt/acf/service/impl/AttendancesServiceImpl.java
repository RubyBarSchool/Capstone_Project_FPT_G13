package com.university.fpt.acf.service.impl;


import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.HistorySalary;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.batik.gvt.CanvasGraphicsNode;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import javax.persistence.SequenceGenerator;
import javax.transaction.Transactional;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AttendancesServiceImpl implements AttendancesService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private AttendancesCustomRepository attendancesCustomRepository;

    @Autowired
    private HistorySalaryRepository historySalaryRepository;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value( "${acf.scross.path}" )
    private String path;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

    //************************************
    // Get all attendance  with combination of fields: name, date, type, note
    //************************************
    @Override
    public List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom) {
        List<AttendanceVO> attendanceVOS = new ArrayList<>();
        try {
            attendanceVOS = attendancesCustomRepository.getAllAttendance(attendanceFrom);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return attendanceVOS;
    }
    //************************************
    // Get total search attendanceFrom
    //************************************
    @Override
    public int getTotalAllAttendance(AttendanceFrom attendanceFrom) {
        if (attendanceFrom.getTotal() != null && attendanceFrom.getTotal() != 0) {
            return attendanceFrom.getTotal().intValue(  );
        }
        int total = 0;
        try {
            total = attendancesCustomRepository.getTotalAttendances(attendanceFrom);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }
    //************************************
    // Save attendance
    //************************************
    @Override
    public List<TimeKeep> saveAttendance(AddAttendanceForm addAttendanceForm) {
        List<TimeKeep> timeKeeps = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            List<Long> idEmployeeAttemdance = attendanceRepository.getAllListID(addAttendanceForm.getDate());
            List<Long> list = new ArrayList<>();
            for (int i = addAttendanceForm.getAttendance().size() - 1; i >= 0; i--) {
                if (idEmployeeAttemdance.contains(addAttendanceForm.getAttendance().get(i).getId())) {
                    addAttendanceForm.getAttendance().remove(i);
                }else{
                    list.add(addAttendanceForm.getAttendance().get(i).getId());
                }
            }
            List<HistorySalary> historySalaryrs = new ArrayList<>();
            for (AttendanceNote addAttendanceForm1 : addAttendanceForm.getAttendance()) {
                TimeKeep timeKeep = new TimeKeep();
                timeKeep.setCreated_by(accountSercurity.getUserName());
                timeKeep.setModified_by(accountSercurity.getUserName());
                timeKeep.setDate(addAttendanceForm.getDate());
                timeKeep.setType(addAttendanceForm.getType());
                timeKeep.setNote(addAttendanceForm1.getNote());


                LocalDate date = addAttendanceForm.getDate();
                HistorySalary historySalary = new HistorySalary();
                if (date.getDayOfMonth() <= 10) {
                    date = date.minusMonths(1);
                    date = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    historySalary = historySalaryRepository.getSalaryByEmployee(addAttendanceForm1.getId(), date);

                } else {
                    date = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                    historySalary = historySalaryRepository.getSalaryByEmployee(addAttendanceForm1.getId(), date);
                }
                Double aDouble = historySalary.getCountWork();
                String total = historySalary.getTotalMoney();
                Integer salary = Integer.parseInt(historySalary.getSalary());
                String totalResult = Double.parseDouble(total) + Double.parseDouble(addAttendanceForm.getType()) * salary + "";
                if (totalResult.length() > 2 && (totalResult.indexOf(".") == (totalResult.length() - 2)) && (totalResult.lastIndexOf("0") == (totalResult.length() - 1))) {
                    totalResult = totalResult.substring(0, totalResult.length() - 2);
                }
                historySalary.setCountWork(aDouble + Double.parseDouble(addAttendanceForm.getType()));
                historySalary.setTotalMoney(totalResult + "");
                if (historySalary != null) {
                    historySalaryrs.add(historySalary);
                }

                Employee e = new Employee();
                e.setId(addAttendanceForm1.getId());
                timeKeep.setEmployee(e);
                timeKeeps.add(timeKeep);
            }
            if (timeKeeps.size() != 0) {
                timeKeeps = attendanceRepository.saveAll(timeKeeps);
                historySalaryRepository.saveAll(historySalaryrs);

                List<String> usernames = accountManagerRepository.getUsernameByIdEmployee(list);
                for(String s : usernames){
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" chấm công cho bạn");
                    notification.setPath("/viewsalary");
                    HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
                }

                List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
                for(String s : accountAdmin){
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setType("success");
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" chấm công ");
                    notification.setPath("/viewattendance");
                    HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
                }

            }
        } catch (Exception ex) {
            log.error("error save Attendance");
            throw new RuntimeException(ex.getMessage());
        }
        return timeKeeps;
    }
    //************************************
    // Update attendance
    //************************************
    @Override
    public TimeKeep updateAttendance(UpdateAttendanceForm updateAttendanceForm) {
        TimeKeep timeKeep = new TimeKeep();
        AccountSercurity accountSercurity = new AccountSercurity();
        try {
            timeKeep = attendanceRepository.getTimeKeepByID(updateAttendanceForm.getId());


            LocalDate localDate = LocalDate.now();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm");
            String dateTimeAfterFormat = dateTime.format(dateTimeFormat);

            Boolean checkUpdate = false;

            if (!timeKeep.getType().equals(updateAttendanceForm.getType())) {
                checkUpdate = true;
            }
            if(timeKeep.getNote() == null && updateAttendanceForm.getNote() != null){
                checkUpdate = true;
            }
            if (timeKeep.getNote() != null && !timeKeep.getNote().equals(updateAttendanceForm.getNote())) {
                checkUpdate = true;
            }

            if (checkUpdate) {
                if (localDate.isAfter(timeKeep.getDate())) {
                    String fullname = accountManagerRepository.getFullnameByUsername(accountSercurity.getUserName());

                    List<Employee> employeeGD = employeeRepository.getEmployeeGD();
                    for (Employee employee : employeeGD) {
                        MimeMessage mimeMessage = emailSender.createMimeMessage();
                        MimeMessageHelper helper =
                                new MimeMessageHelper(mimeMessage, "utf-8");
                        helper.setText(this.buildEmail(timeKeep.getEmployee().getFullName(),timeKeep.getDate(),timeKeep.getType(),updateAttendanceForm.getType(),timeKeep.getNote(),updateAttendanceForm.getNote(),employee.getFullName(),path+ "/#/viewattendance"), true);
                        helper.setTo(employee.getEmail());
                        helper.setSubject("Phát hiện chỉnh sửa điểm danh đã quá hạn bởi quản lý " + fullname + " vào " + dateTimeAfterFormat);
                        emailSender.send(mimeMessage);
                    }
                }
            }


            LocalDate dateCheck = LocalDate.now();
            if (dateCheck.getDayOfMonth() <= 10) {
                dateCheck = dateCheck.minusMonths(1);
                dateCheck = LocalDate.of(dateCheck.getYear(), dateCheck.getMonthValue(), 10);
            } else {
                dateCheck = LocalDate.of(dateCheck.getYear(), dateCheck.getMonthValue(), 10);
            }
            if (timeKeep.getDate().isBefore(dateCheck)) {
                throw new RuntimeException("Không thể sửa điểm danh của tháng trước");
            }

            LocalDate date = timeKeep.getDate();
            HistorySalary historySalary = new HistorySalary();
            if (date.getDayOfMonth() <= 10) {
                date = date.minusMonths(1);
                date = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                historySalary = historySalaryRepository.getSalaryByEmployee(timeKeep.getEmployee().getId(), date);

            } else {
                date = LocalDate.of(date.getYear(), date.getMonthValue(), 10);
                historySalary = historySalaryRepository.getSalaryByEmployee(timeKeep.getEmployee().getId(), date);
            }
            Double aDouble = historySalary.getCountWork();
            String total = historySalary.getTotalMoney();
            Integer salary = Integer.parseInt(historySalary.getSalary());

            Double countOld = aDouble - Double.parseDouble(timeKeep.getType());

            Double typeOld = Double.parseDouble(timeKeep.getType());

            Double totalOld = Double.parseDouble(total) - typeOld * salary;

            String totalResult = totalOld + Double.parseDouble(updateAttendanceForm.getType()) * salary + "";

            if (totalResult.length() > 2 && (totalResult.indexOf(".") == (totalResult.length() - 2)) && (totalResult.lastIndexOf("0") == (totalResult.length() - 1))) {
                totalResult = totalResult.substring(0, totalResult.length() - 2);
            }
            historySalary.setCountWork(countOld + Double.parseDouble(updateAttendanceForm.getType()));
            historySalary.setTotalMoney(totalResult + "");

            timeKeep.setModified_by(accountSercurity.getUserName());
            timeKeep.setModified_date(LocalDate.now());
            timeKeep.setType(updateAttendanceForm.getType());
            timeKeep.setNote(updateAttendanceForm.getNote());
            timeKeep = attendanceRepository.save(timeKeep);
            historySalaryRepository.save(historySalary);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return timeKeep;
    }
    //************************************
    // Build email
    //************************************
    private String buildEmail(String fullNameChange,LocalDate dateChange,String typeOld,String typeNew,String noteOld,String noteNew, String fullName, String link) {
        StringBuilder sql = new StringBuilder("");
        sql.append("<div style=\" width:80%; margin: 0 auto;\">\n" +
                "        <img src=\"\">\n" +
                "        <table style=\"width:100%;\">\n" +
                "            <tr>\n" +
                "                <td colspan=\" 2 \">Xin chào " + fullName + ", </td>\n" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td> Nhân viên được thay đổi số liệu: "+fullNameChange+"</td>" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td> Ngày sửa đổi: "+dateChange+"</td>" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td> Số công: "+typeOld+"    =>   "+typeNew+"</td>" +
                "            </tr>\n" +
                "            <tr>\n" +
                "                <td> Ghi chú: "+noteOld+"    =>   "+noteNew+"</td>" +
                "            </tr>\n" +
                "        </table>\n" +
                "        <br>\n" +
                "        <p>vui lòng mời bạn nhấn vào đường dẫn bên dưới để đến với điểm danh của công ty. </p>\n" +
                "        <button style=\"display: block; margin-left: auto; margin-right: auto; background-color: #40A9FF; color: white;\">" +
                "      <a href=\"" + link + "\">Đăng nhập!</a></button>\n" +
                "        <p>Trân trọng,</p>\n" +
                "        <h3 style=\"font-family: 'Courier New', Courier, monospace \">Anh Chung Furniture</h3>\n" +
                "    </div>");
        return sql.toString();
    }
    //************************************
    // Preview excel
    //************************************
    @Override
    public List<Object> priviewExcel(ExportExcelForm exportExcelForm) {
        List<AttendanceVO> attendanceVOS = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy");
        List<Object> sheets = new ArrayList<>();
        List<LinkedHashMap<String, String>> row = new ArrayList<>();
        List<Long> idNhanVien = new ArrayList<>();
        List<LocalDate> localDates = new ArrayList<>();
        List<Integer> month = new ArrayList<>();
        try {
            attendanceVOS = attendancesCustomRepository.priviewDetailExcel(exportExcelForm);
            for (AttendanceVO attendanceVO : attendanceVOS) {
                if (!idNhanVien.contains(attendanceVO.getId())) {
                    idNhanVien.add(attendanceVO.getId());
                }
                if (!localDates.contains(attendanceVO.getDate())) {
                    localDates.add(attendanceVO.getDate());
                }
                if (!month.contains(attendanceVO.getDate().getMonthValue())) {
                    month.add(attendanceVO.getDate().getMonthValue());
                }
            }
            // load data
            for (AttendanceVO attendanceVO : attendanceVOS) {
                Boolean checkExitData = true;
                for (LinkedHashMap<String, String> o : row) {
                    if (o.get("SỐ THỨ TỰ").equals(attendanceVO.getIdEmpl().toString())) {
                        if (exportExcelForm.getNote().equals("true")) {
                            if (!attendanceVO.getNote().isBlank()) {
                                o.put("NOTE", (o.get("NOTE").isBlank() ? "" : o.get("NOTE")) + attendanceVO.getDate().format(dateFormatter) + ": " + attendanceVO.getNote() + "\n");
                            }
                        }
                        o.put(attendanceVO.getDate().format(dateFormatter), attendanceVO.getType());
                        checkExitData = false;
                        break;
                    }
                }
                if (checkExitData) {
                    LinkedHashMap<String, String> dataAttendance = new LinkedHashMap<>();
                    dataAttendance.put("SỐ THỨ TỰ", attendanceVO.getIdEmpl().toString());
                    dataAttendance.put("HỌ VÀ TÊN", attendanceVO.getNameEmpl());
                    if (exportExcelForm.getNote().equals("true")) {
                        if (!attendanceVO.getNote().isBlank()) {
                            dataAttendance.put("NOTE", attendanceVO.getDate().format(dateFormatter) + ": " + attendanceVO.getNote() + "\n");
                        } else {
                            dataAttendance.put("NOTE", "");
                        }
                    }
                    for (LocalDate localDate : localDates) {
                        dataAttendance.put(localDate.format(dateFormatter), "");
                    }
                    dataAttendance.put(attendanceVO.getDate().format(dateFormatter), attendanceVO.getType());
                    row.add(dataAttendance);
                }
            }
            sheets.add(row);
//                } else {
//                    for (int i = 0; i < attendanceVOS.size(); i++) {
//                        if (i != 0 && (attendanceVOS.get(i).getDate().getMonthValue() != attendanceVOS.get(i - 1).getDate().getMonthValue())) {
//                            sheets.add(row);
//                            row = new ArrayList<>();
//                        }
//                        Boolean checkExitData = true;
//                        for (LinkedHashMap<String, String> o : row) {
//                            if (o.get("SỐ THỨ TỰ").equals(attendanceVOS.get(i).getIdEmpl().toString())) {
//                                if (exportExcelForm.getNote().equals("true")) {
//                                    if (!attendanceVOS.get(i).getNote().isBlank()) {
//                                        o.put("NOTE", (o.get("NOTE").isBlank() ? "" : o.get("NOTE")) + attendanceVOS.get(i).getDate().format(dateFormatter) + ": " + attendanceVOS.get(i).getNote() + "\n");
//                                    }
//                                }
//                                o.put(attendanceVOS.get(i).getDate().format(dateFormatter), attendanceVOS.get(i).getType());
//                                checkExitData = false;
//                                break;
//                            }
//                        }
//                        if (checkExitData) {
//                            LinkedHashMap<String, String> dataAttendance = new LinkedHashMap<>();
//                            dataAttendance.put("SỐ THỨ TỰ", attendanceVOS.get(i).getIdEmpl().toString());
//                            dataAttendance.put("HỌ VÀ TÊN", attendanceVOS.get(i).getNameEmpl());
//                            if (exportExcelForm.getNote().equals("true")) {
//                                if (!attendanceVOS.get(i).getNote().isBlank()) {
//                                    dataAttendance.put("NOTE", attendanceVOS.get(i).getDate().format(dateFormatter) + ": " + attendanceVOS.get(i).getNote() + "\n");
//                                }else{
//                                    dataAttendance.put("NOTE", "");
//                                }
//                            }
//                            for (LocalDate localDate : localDates) {
//                                if(localDate.getMonthValue() == month.get(sheets.size())){
//                                    dataAttendance.put(localDate.format(dateFormatter), "");
//                                }
//                            }
//                            dataAttendance.put(attendanceVOS.get(i).getDate().format(dateFormatter), attendanceVOS.get(i).getType());
//                            row.add(dataAttendance);
//                        }
//
//                    }
//                    sheets.add(row);
//                }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheets;
    }
    //************************************
    // Download excel
    //************************************
    @Override
    public ByteArrayInputStream downExcel(ExportExcelForm exportExcelForm) {
        List<AttendanceVO> attendanceVOS = new ArrayList<>();
        DateTimeFormatter dateFormatter = DateTimeFormatter
                .ofPattern("dd-MM-yyyy");
        List<LinkedHashMap<String, String>> row = new ArrayList<>();
        List<Long> idNhanVien = new ArrayList<>();
        List<LocalDate> localDates = new ArrayList<>();
        try {
            attendanceVOS = attendancesCustomRepository.priviewDetailExcel(exportExcelForm);
            for (AttendanceVO attendanceVO : attendanceVOS) {
                if (!idNhanVien.contains(attendanceVO.getId())) {
                    idNhanVien.add(attendanceVO.getId());
                }
                if (!localDates.contains(attendanceVO.getDate())) {
                    localDates.add(attendanceVO.getDate());
                }
            }
            // load data
            for (AttendanceVO attendanceVO : attendanceVOS) {
                Boolean checkExitData = true;
                for (LinkedHashMap<String, String> o : row) {
                    if (o.get("SỐ THỨ TỰ").equals(attendanceVO.getIdEmpl().toString())) {
                        if (exportExcelForm.getNote().equals("true")) {
                            if (attendanceVO.getNote() !=  null && !attendanceVO.getNote().isBlank()) {
                                o.put("NOTE", (o.get("NOTE").isBlank() ? "" : o.get("NOTE"))
                                        + attendanceVO.getDate().format(dateFormatter) + ": " + attendanceVO.getNote()
                                        + "\n");
                            }
                        }
                        o.put("TỔNG SỐ CÔNG", Double.parseDouble(o.get("TỔNG SỐ CÔNG")) + Double.parseDouble(attendanceVO.getType()) +"" );
                        o.put(attendanceVO.getDate().format(dateFormatter), attendanceVO.getType());
                        checkExitData = false;
                        break;
                    }
                }
                if (checkExitData) {
                    LinkedHashMap<String, String> dataAttendance = new LinkedHashMap<>();
                    dataAttendance.put("SỐ THỨ TỰ", attendanceVO.getIdEmpl().toString());
                    dataAttendance.put("HỌ VÀ TÊN", attendanceVO.getNameEmpl());
                    dataAttendance.put("TỔNG SỐ CÔNG", attendanceVO.getType());
                    if (exportExcelForm.getNote().equals("true")) {
                        if (attendanceVO.getNote() != null && !attendanceVO.getNote().isBlank()) {
                            dataAttendance.put("NOTE", attendanceVO.getDate().format(dateFormatter)
                                    + ": " + attendanceVO.getNote() + "\n");
                        } else {
                            dataAttendance.put("NOTE", "");
                        }
                    }
                    for (LocalDate localDate : localDates) {
                        dataAttendance.put(localDate.format(dateFormatter), "");
                    }
                    dataAttendance.put(attendanceVO.getDate().format(dateFormatter), attendanceVO.getType());
                    row.add(dataAttendance);
                }
            }
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Attendance");
            // Create font
            Font font = sheet.getWorkbook().createFont();
            font.setFontName("Times New Roman");
            font.setBold(true);
            font.setFontHeightInPoints((short) 14);
            font.setColor(IndexedColors.WHITE.getIndex());

            // Create CellStyle
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            cellStyle.setFont(font);
            cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);


            CellStyle cellStyleData = sheet.getWorkbook().createCellStyle();
            cellStyleData.setWrapText(true);
            cellStyleData.setAlignment(HorizontalAlignment.LEFT);
            cellStyleData.setVerticalAlignment(VerticalAlignment.CENTER);


            int rowIndex = 0;
            for (int i = 0; i < row.size(); i++) {
                int cellIndex = 0;
                if (i == 0) {
                    Row rowx = sheet.createRow(rowIndex++);
                    int k = 0;
                    for (String nameCollumn : row.get(i).keySet()) {
                        sheet.setColumnWidth(k,6000);
                        if(nameCollumn.equals("NOTE")){
                            sheet.setColumnWidth(k,10000);
                        }
                        sheet.autoSizeColumn(k);
                        Cell cell1 = rowx.createCell(cellIndex);
                        cell1.setCellStyle(cellStyle);
                        cell1.setCellValue(nameCollumn);

                        cellIndex++;
                        k++;
                    }
                    cellIndex = 0;
                }
                Row rowx = sheet.createRow(rowIndex++);
                for (String nameCollumn : row.get(i).keySet()) {
                    Cell cell1 = rowx.createCell(cellIndex);
                    cell1.setCellStyle(cellStyleData);
                    if( nameCollumn.equals("HỌ VÀ TÊN")||nameCollumn.equals("NOTE")){
                        cell1.setCellValue(row.get(i).get(nameCollumn));
                    }else if(nameCollumn.equals("SỐ THỨ TỰ")){
                        cell1.setCellValue(Integer.parseInt(row.get(i).get(nameCollumn)));
                    }else{
                        String value = row.get(i).get(nameCollumn);
                        if(value.length()==3){
                            cell1.setCellValue(Double.parseDouble(value));
                        }else if(value.length()==1){
                            cell1.setCellValue(Integer.parseInt(value));
                        }else{
                            cell1.setCellValue(value);
                        }
                    }
                    cellIndex++;
                }
            }
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
