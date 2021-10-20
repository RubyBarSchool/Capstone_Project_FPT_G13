package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.vo.AttendanceVO;

import java.util.List;

public interface AttendancesService {
    List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom);
}
