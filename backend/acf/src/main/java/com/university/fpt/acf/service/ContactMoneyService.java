package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddContactMoneyForm;
import com.university.fpt.acf.form.SearchCompanyForm;
import com.university.fpt.acf.form.SearchContactMoneyForm;
import com.university.fpt.acf.vo.CompanyVO;

import java.util.HashMap;
import java.util.List;

public interface ContactMoneyService {
    List<HashMap<String,Object>> searchContactMoney(SearchContactMoneyForm searchContactMoneyForm);
    int getTotalSearchContactMoney(SearchContactMoneyForm searchContactMoneyForm);

    Boolean addContactMoney(AddContactMoneyForm addContactMoneyForm);

    Boolean editContactMoney(AddContactMoneyForm addContactMoneyForm);

    Boolean deleteContactMoney(Long id);

    Boolean confirmContactMoney(AddContactMoneyForm addContactMoneyForm);
}
