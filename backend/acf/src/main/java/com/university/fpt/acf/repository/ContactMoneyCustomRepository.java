package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.ContactMoney;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.form.SearchContactMoneyForm;

import java.util.List;

public interface ContactMoneyCustomRepository {
    List<ContactMoney> searchContactMoney(SearchContactMoneyForm searchContactMoneyForm);
    int getTotalsearchContactMoney(SearchContactMoneyForm searchContactMoneyForm);
}
