package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.form.SearchContactDetailForm;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.SearchContactDetailVO;

import java.util.List;

public interface ContactCustomRepository {
    List<ContactVO> searchContact(ContactInSearchForm contactInSearchForm);

    int getTotalSearchContact(ContactInSearchForm contactInSearchForm);

    List<SearchContactDetailVO> searchContactDetail(SearchContactDetailForm searchContactDetailForm);

    int getTotalSearchContactDetail(SearchContactDetailForm searchContactDetailForm);
}
