package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.form.AddContactForm;
import com.university.fpt.acf.vo.FileContactVO;
import org.springframework.web.multipart.MultipartFile;

public interface ContactService {
    Contact addContact(AddContactForm addContactForm);
    FileContactVO readFileContact(MultipartFile file);
}
