package com.university.fpt.acf.service.impl;


import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.AddAttendanceForm;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.AttendanceNote;
import com.university.fpt.acf.repository.AttendanceRepository;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.vo.AttendanceVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class AttendancesServiceImpl implements AttendancesService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Override
    public List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom) {
        return null;
    }

    @Override
    public List<TimeKeep> saveAttendance(AddAttendanceForm addAttendanceForm) {
        List<TimeKeep> timeKeeps = new ArrayList<>();
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            List<Long> idEmployeeAttemdance  = attendanceRepository.getAllListID(addAttendanceForm.getDate());
            for(int i = addAttendanceForm.getAttendance().size()-1 ; i >= 0  ; i --){
                if(idEmployeeAttemdance.contains(addAttendanceForm.getAttendance().get(i).getId())){
                    addAttendanceForm.getAttendance().remove(i);
                }
            }
            for(AttendanceNote addAttendanceForm1 : addAttendanceForm.getAttendance()){
                TimeKeep  timeKeep = new TimeKeep();
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
            if(timeKeeps.size()!=0){
                timeKeeps = attendanceRepository.saveAll(timeKeeps);
            }
        }catch (Exception ex){
            log.error("error save Attendance");
            throw new RuntimeException(ex.getMessage());
        }
        return timeKeeps;
    }
}
