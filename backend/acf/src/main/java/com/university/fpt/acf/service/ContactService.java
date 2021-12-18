package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ContactService {
    Contact addContact(AddContactForm addContactForm);

    List<ContactVO> searchContact(ContactInSearchForm contactInSearchForm);

    List<ContactVO> searchContactMmoney(ContactInSearchForm contactInSearchForm);

    List<ContactProductionVO> searchContactProduction();

    List<ContactVO> searchContactNotDone();

    int getTotalSearchContact(ContactInSearchForm contactInSearchForm);

    List<SearchContactDetailVO> searchContactDetail(SearchContactDetailForm searchContactDetailForm);

    List<MaterialInContactDetailVO> getMaterialInProduct(Long idProduct);


    int getTotalSearchContactDetail(SearchContactDetailForm searchContactDetailForm);

    FileContactVO readFileContact(MultipartFile file);

    List<GetCreateContactVO> searchCreateContact(SearchCreateContactFrom searchForm);

    int totalSearchCreateContact(SearchCreateContactFrom search);
    Boolean updateContact(UpdateContractForm updateForm);
    Boolean deleteContact(Long id);

    ByteArrayInputStream exportContact(Long id);
    ByteArrayInputStream templateContact(Long id);
}
