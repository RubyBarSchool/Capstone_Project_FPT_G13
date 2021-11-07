package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AcceptPersonalLeaveApplicationAdminForm;
import com.university.fpt.acf.form.SearchPersonalLeaveAdminApplicationForm;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;

import java.util.List;

public interface PersonalLeaveApplicationAdminService {
    List<SearchPersonalLeaveApplicationAdminVO> searchPersonalApplication(SearchPersonalLeaveAdminApplicationForm personalApplicationForm);
    int totalPersonalApplication(SearchPersonalLeaveAdminApplicationForm personalApplicationForm);
    Boolean acceptPersonalLeaveApplication(AcceptPersonalLeaveApplicationAdminForm acceptForm);
    Boolean rejectPersonalLeaveApplication(AcceptPersonalLeaveApplicationAdminForm acceptForm);
//    SearchPersonalLeaveApplicationAdminVO detailPersonalApplicationById(Long id);
}
