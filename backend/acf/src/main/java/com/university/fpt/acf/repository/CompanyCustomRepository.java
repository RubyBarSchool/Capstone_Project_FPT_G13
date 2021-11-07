package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.CompanyVO;

import java.util.List;

public interface CompanyCustomRepository {
    List<CompanyVO> searchCompany(SearchCompanyForm searchCompanyForm);
    int getTotalSearchCompany(SearchCompanyForm searchCompanyForm);

}
