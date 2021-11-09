package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddCompanyForm;
import com.university.fpt.acf.form.SearchCompanyForm;
import com.university.fpt.acf.form.UpdateCompanyForm;
import com.university.fpt.acf.vo.CompanyVO;

import java.util.List;

public interface CompanyService {
    List<CompanyVO> searchCompany(SearchCompanyForm searchCompanyForm);
    int getTotalSearchCompany(SearchCompanyForm searchCompanyForm);
    Boolean insertCompany(AddCompanyForm addCompanyForm);
    Boolean updateCompany(UpdateCompanyForm updateCompanyForm);
    Boolean deleteCompany(Long id);
}
