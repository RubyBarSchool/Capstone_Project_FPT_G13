package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchPersonalLeaveAdminApplicationForm;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;

import java.util.List;

public interface PersonalLeaveApplicationAdminCustomRepository {
    List<SearchPersonalLeaveApplicationAdminVO> searchApplication(SearchPersonalLeaveAdminApplicationForm searchApplication);
    int totalSearchApplication(SearchPersonalLeaveAdminApplicationForm searchApplication);
}
