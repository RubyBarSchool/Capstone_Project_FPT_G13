package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Color;
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
        return  listCompany;
    }

    @Override
    public int getTotalSearchCompany(SearchCompanyForm searchCompanyForm) {
        int size = 0;
        try {
            size = companyCustomRepository.getTotalSearchCompany(searchCompanyForm);
        }catch (Exception e){
            throw new RuntimeException("Could not initialize folder for upload!");
        }
        return size;
    }

    @Override
    public Boolean insertCompany(AddCompanyForm addCompanyForm) {
        boolean check = false;
        try{
            if(addCompanyForm.getAddress()==null && addCompanyForm.getName()==null
            && addCompanyForm.getPhone()==null && addCompanyForm.getEmail()==null){
                throw new Exception("Data company is null");
            }else{
                if(companyRespository.checkExitCompanyByName(addCompanyForm.getName())==null||
                        companyRespository.checkExitCompanyByName(addCompanyForm.getName()).isEmpty()){
                    Company c = new Company();
                    c.setName(addCompanyForm.getName());
                    c.setAddress(addCompanyForm.getAddress());
                    c.setEmail(addCompanyForm.getEmail());
                    c.setPhone(addCompanyForm.getPhone());
                    AccountSercurity accountSercurity = new AccountSercurity();
                    c.setModified_by(accountSercurity.getUserName());
                    c.setCreated_by(accountSercurity.getUserName());
                    companyRespository.save(c);
                    check = true;
                }else {
                    throw new Exception("Company da ton tai");
                }

            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateCompany(UpdateCompanyForm updateCompanyForm) {
        boolean check = false;
        try{
            if(updateCompanyForm.getAddress()==null && updateCompanyForm.getName()==null
                    && updateCompanyForm.getPhone()==null && updateCompanyForm.getEmail()==null){
                throw new Exception("Data company is null");
            }else{
                if(companyRespository.checkExitCompanyById(updateCompanyForm.getName())!= updateCompanyForm.getId()){
                    throw new Exception("Company da ton tai");
                }
                    Company c = companyRespository.getCompanyById(updateCompanyForm.getId());
                    if(c!=null){
                        c.setName(updateCompanyForm.getName());
                        c.setAddress(updateCompanyForm.getAddress());
                        c.setEmail(updateCompanyForm.getEmail());
                        c.setPhone(updateCompanyForm.getPhone());
                        AccountSercurity accountSercurity = new AccountSercurity();
                        c.setModified_by(accountSercurity.getUserName());
                        c.setModified_date(LocalDate.now());
                        companyRespository.save(c);
                        check = true;
                    }else {
                        throw new Exception("Ko tim thay company");
                    }
            }
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deleteCompany(Long id) {
        boolean check = false;
        try{
            Company c = companyRespository.getCompanyById(id);
                    if(c!=null){
                        Long checkCompany =companyRespository.checkExitCompanyInColor(id);
                        if(checkCompany==0){
                            c.setDeleted(true);
                            AccountSercurity accountSercurity = new AccountSercurity();
                            c.setModified_by(accountSercurity.getUserName());
                            c.setModified_date(LocalDate.now());
                            companyRespository.save(c);
                            check = true;
                        }else {
                            throw new Exception("Ko the xoa vi id_company ton tai trong color");
                        }

                    }else {
                        throw new Exception("Ko tim thay company");
                    }


        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return check;
    }
}
