package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ExportExcelForm;
import com.university.fpt.acf.vo.AttendanceVO;

import java.util.List;

public interface AttendancesCustomRepository {
    List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom);

    List<AttendanceVO> priviewDetailExcel(ExportExcelForm exportExcelForm);

    int getTotalAttendances(AttendanceFrom attendanceFrom);
}
