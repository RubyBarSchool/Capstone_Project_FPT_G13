package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.vo.ContactVO;

import java.util.List;

public interface ContactCustomRepository {
    List<ContactVO> searchContact(ContactInSearchForm contactInSearchForm);

    int getTotalSearchContact(ContactInSearchForm contactInSearchForm);
}
