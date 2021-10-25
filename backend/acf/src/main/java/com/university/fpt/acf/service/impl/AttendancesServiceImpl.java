package com.university.fpt.acf.service.impl;


import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.AttendanceRepository;
import com.university.fpt.acf.repository.AttendancesCustomRepository;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.vo.AttendanceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;
import javax.transaction.Transactional;
import java.time.LocalDate;
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

    @Override
    public int getTotalAllAttendance(AttendanceFrom attendanceFrom) {
        if (attendanceFrom.getTotal() != null && attendanceFrom.getTotal() != 0) {
            return attendanceFrom.getTotal().intValue();
        }
        int total = 0;
        try {
            total = attendancesCustomRepository.getTotalAttendances(attendanceFrom);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public List<TimeKeep> saveAttendance(AddAttendanceForm addAttendanceForm) {
        List<TimeKeep> timeKeeps = new ArrayList<>();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            List<Long> idEmployeeAttemdance = attendanceRepository.getAllListID(addAttendanceForm.getDate());
            for (int i = addAttendanceForm.getAttendance().size() - 1; i >= 0; i--) {
                if (idEmployeeAttemdance.contains(addAttendanceForm.getAttendance().get(i).getId())) {
                    addAttendanceForm.getAttendance().remove(i);
                }
            }
            for (AttendanceNote addAttendanceForm1 : addAttendanceForm.getAttendance()) {
                TimeKeep timeKeep = new TimeKeep();
                timeKeep.setCreated_by(accountSercurity.getUserName());
                timeKeep.setModified_by(accountSercurity.getUserName());
                timeKeep.setDate(addAttendanceForm.getDate());
                timeKeep.setType(addAttendanceForm.getType());
                timeKeep.setNote(addAttendanceForm1.getNote());
                Employee e = new Employee();
                e.setId(addAttendanceForm1.getId());
                timeKeep.setEmployee(e);
                timeKeeps.add(timeKeep);
            }
            if (timeKeeps.size() != 0) {
                timeKeeps = attendanceRepository.saveAll(timeKeeps);
            }
        } catch (Exception ex) {
            log.error("error save Attendance");
            throw new RuntimeException(ex.getMessage());
        }
        return timeKeeps;
    }

    @Override
    public TimeKeep updateAttendance(UpdateAttendanceForm updateAttendanceForm) {
        TimeKeep timeKeep = new TimeKeep();
        AccountSercurity accountSercurity = new AccountSercurity();
        try {
            timeKeep = attendanceRepository.getById(updateAttendanceForm.getId());
            timeKeep.setModified_by(accountSercurity.getUserName());
            timeKeep.setModified_date(LocalDate.now());
            timeKeep.setType(updateAttendanceForm.getType());
            timeKeep.setNote(updateAttendanceForm.getNote());
            timeKeep = attendanceRepository.save(timeKeep);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return timeKeep;
    }

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
            if (exportExcelForm.getStructure().equals("horizontal")) {
                if (exportExcelForm.getPaging().equals("false")) {
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
                                }else{
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
                } else {
                    for (int i = 0; i < attendanceVOS.size(); i++) {
                        if (i != 0 && (attendanceVOS.get(i).getDate().getMonthValue() != attendanceVOS.get(i - 1).getDate().getMonthValue())) {
                            sheets.add(row);
                            row = new ArrayList<>();
                        }
                        Boolean checkExitData = true;
                        for (LinkedHashMap<String, String> o : row) {
                            if (o.get("SỐ THỨ TỰ").equals(attendanceVOS.get(i).getIdEmpl().toString())) {
                                if (exportExcelForm.getNote().equals("true")) {
                                    if (!attendanceVOS.get(i).getNote().isBlank()) {
                                        o.put("NOTE", (o.get("NOTE").isBlank() ? "" : o.get("NOTE")) + attendanceVOS.get(i).getDate().format(dateFormatter) + ": " + attendanceVOS.get(i).getNote() + "\n");
                                    }
                                }
                                o.put(attendanceVOS.get(i).getDate().format(dateFormatter), attendanceVOS.get(i).getType());
                                checkExitData = false;
                                break;
                            }
                        }
                        if (checkExitData) {
                            LinkedHashMap<String, String> dataAttendance = new LinkedHashMap<>();
                            dataAttendance.put("SỐ THỨ TỰ", attendanceVOS.get(i).getIdEmpl().toString());
                            dataAttendance.put("HỌ VÀ TÊN", attendanceVOS.get(i).getNameEmpl());
                            if (exportExcelForm.getNote().equals("true")) {
                                if (!attendanceVOS.get(i).getNote().isBlank()) {
                                    dataAttendance.put("NOTE", attendanceVOS.get(i).getDate().format(dateFormatter) + ": " + attendanceVOS.get(i).getNote() + "\n");
                                }else{
                                    dataAttendance.put("NOTE", "");
                                }
                            }
                            for (LocalDate localDate : localDates) {
                                dataAttendance.put(localDate.format(dateFormatter), "");
                            }
                            dataAttendance.put(attendanceVOS.get(i).getDate().format(dateFormatter), attendanceVOS.get(i).getType());
                            row.add(dataAttendance);
                        }

                    }
                    sheets.add(row);
                }
            } else {

            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheets;
    }
}
