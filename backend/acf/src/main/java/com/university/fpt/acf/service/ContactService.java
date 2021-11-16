package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.form.AddContactForm;
import com.university.fpt.acf.form.AttendanceFrom;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.vo.ContactVO;
import com.university.fpt.acf.vo.FileContactVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ContactService {
    Contact addContact(AddContactForm addContactForm);

    List<ContactVO> searchContact(ContactInSearchForm contactInSearchForm);

    int getTotalSearchContact(ContactInSearchForm contactInSearchForm);

    FileContactVO readFileContact(MultipartFile file);
}
