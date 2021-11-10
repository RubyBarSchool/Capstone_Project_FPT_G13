package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Company;
import com.university.fpt.acf.form.AddCompanyForm;
import com.university.fpt.acf.form.SearchCompanyForm;
import com.university.fpt.acf.form.UpdateCompanyForm;
import com.university.fpt.acf.repository.CompanyCustomRepository;
import com.university.fpt.acf.repository.CompanyRespository;
import com.university.fpt.acf.service.CompanyService;
import com.university.fpt.acf.vo.CompanyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRespository companyRespository;
    @Autowired
    private CompanyCustomRepository companyCustomRepository;

    @Override
    public List<CompanyVO> searchCompany(SearchCompanyForm searchCompanyForm) {
        List<CompanyVO> listCompany = new ArrayList<>();
        try {
            listCompany = companyCustomRepository.searchCompany(searchCompanyForm);
        } catch (Exception e) {
            throw new RuntimeException("Error position repository " + e.getMessage());
        }
        return listCompany;
    }

    @Override
    public int getTotalSearchCompany(SearchCompanyForm searchCompanyForm) {
        int size = 0;
        try {
            size = companyCustomRepository.getTotalSearchCompany(searchCompanyForm);
        } catch (Exception e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }

    @Override
    public Boolean insertCompany(AddCompanyForm addCompanyForm) {
        boolean check = false;
        try {
            if (addCompanyForm.getAddress() == null && addCompanyForm.getName() == null
                    && addCompanyForm.getPhone() == null && addCompanyForm.getEmail() == null) {
                throw new Exception("Data company is null");
            }
            String checkNameCompany = companyRespository.checkExitCompanyByName(addCompanyForm.getName());
            if (checkNameCompany != null && !checkNameCompany.isEmpty()) {
                throw new Exception("Company da ton tai");
            }
            String checkPhone = companyRespository.checkExitPhoneCompanyByPhone(addCompanyForm.getPhone());
            if (checkPhone != null && !checkPhone.isEmpty()) {
                throw new Exception("phone da ton tai");
            }
            String checkEmail = companyRespository.checkExitEmailCompanyByEmail(addCompanyForm.getEmail());
            if (checkEmail != null && !checkEmail.isEmpty()) {
                throw new Exception("email da ton tai");
            }
            Company c = new Company();
            c.setName(addCompanyForm.getName());
            c.setAddress(addCompanyForm.getAddress());
            c.setEmail(addCompanyForm.getEmail());
            c.setPhone(addCompanyForm.getPhone());
            AccountSercurity accountSercurity = new AccountSercurity();
            c.setCreated_date(LocalDate.now());;
            c.setCreated_by(accountSercurity.getUserName());
            companyRespository.save(c);
            check = true;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateCompany(UpdateCompanyForm updateCompanyForm) {
        boolean check = false;
        try {
            if (updateCompanyForm.getAddress() == null && updateCompanyForm.getName() == null
                    && updateCompanyForm.getPhone() == null && updateCompanyForm.getEmail() == null) {
                throw new Exception("Data company is null");
            }
            Long id = companyRespository.checkExitCompanyById(updateCompanyForm.getName());
            if (id != updateCompanyForm.getId() && id != null) {
                throw new Exception("Company da ton tai");
            }
            Long checkPhone = companyRespository.checkExitPhoneCompanyById(updateCompanyForm.getPhone());
            if (checkPhone != updateCompanyForm.getId() && checkPhone != null) {
                throw new Exception("Phone da ton tai");
            }
            Long checkEmail = companyRespository.checkExitEmailCompanyById(updateCompanyForm.getEmail());
            if (checkEmail != updateCompanyForm.getId() && checkEmail != null) {
                throw new Exception("Email da ton tai");
            }
            Company c = companyRespository.getCompanyById(updateCompanyForm.getId());
            if (c != null) {
                c.setName(updateCompanyForm.getName());
                c.setAddress(updateCompanyForm.getAddress());
                c.setEmail(updateCompanyForm.getEmail());
                c.setPhone(updateCompanyForm.getPhone());
                AccountSercurity accountSercurity = new AccountSercurity();
                c.setModified_by(accountSercurity.getUserName());
                c.setModified_date(LocalDate.now());
                companyRespository.save(c);
                check = true;
            } else {
                throw new Exception("Ko tim thay company");
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deleteCompany(Long id) {
        boolean check = false;
        try {
            Company c = companyRespository.getCompanyById(id);
            if (c != null) {
                c.setDeleted(true);
                AccountSercurity accountSercurity = new AccountSercurity();
                c.setModified_by(accountSercurity.getUserName());
                c.setModified_date(LocalDate.now());
                companyRespository.save(c);
                check = true;
            } else {
                throw new Exception("Ko tim thay company");
            }


        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}
