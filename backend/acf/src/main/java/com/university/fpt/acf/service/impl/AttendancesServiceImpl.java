package com.university.fpt.acf.service.impl;


import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.service.AttendancesService;
import com.university.fpt.acf.vo.AttendanceVO;
import org.springframework.stereotype.Service;

import javax.persistence.SequenceGenerator;
import java.util.List;

@Service
public class AttendancesServiceImpl implements AttendancesService {
    @Override
    public List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom) {
        return null;
    }
}
