package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.TimeKeep;
import com.university.fpt.acf.form.AddAttendanceForm;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ExportExcelForm;
import com.university.fpt.acf.form.UpdateAttendanceForm;
import com.university.fpt.acf.vo.AttendanceVO;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;

public interface AttendancesService {
    List<AttendanceVO> getAllAttendance(AttendanceFrom attendanceFrom);
    int getTotalAllAttendance(AttendanceFrom attendanceFrom);
    List<TimeKeep> saveAttendance(AddAttendanceForm addAttendanceForm);
    TimeKeep updateAttendance(UpdateAttendanceForm updateAttendanceForm);
    List<Object> priviewExcel(ExportExcelForm exportExcelForm);
    ByteArrayInputStream downExcel(ExportExcelForm exportExcelForm);
}
