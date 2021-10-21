package com.university.fpt.acf.service.impl;


import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.AddAttendanceForm;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.repository.AttendanceRepository;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.vo.AttendanceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;
import java.util.List;

@Service
public class AttendancesServiceImpl implements AttendancesService {
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Override
    public List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom) {
        return null;
    }

    @Override
    public List<TimeKeep> saveAttendance(AddAttendanceForm addAttendanceForm) {
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            List<Long> idEmployeeAttemdance  = attendanceRepository.getAllListID(addAttendanceForm.getDate());
            for(int i = addAttendanceForm.getId().size()-1 ; i >= 0  ; i --){
                if(idEmployeeAttemdance.contains(addAttendanceForm.getId().get(i))){
                    addAttendanceForm.getId().remove(i);
                }
            }

            TimeKeep  timeKeep = new TimeKeep();
            timeKeep.setCreated_by(accountSercurity.getUserName());
            timeKeep.setModified_by(accountSercurity.getUserName());

        }catch (Exception ex){

        }
        return null;
    }
}
