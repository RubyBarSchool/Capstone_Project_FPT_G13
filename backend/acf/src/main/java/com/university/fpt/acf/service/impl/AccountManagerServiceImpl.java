package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.AccountCustomRepository;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.repository.EmployeeRepository;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.util.AccountValidate.AddAccountValidate;
import com.university.fpt.acf.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AccountManagerServiceImpl implements AccountManagerService {
    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private AccountCustomRepository accountCustomRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Boolean insertAccount(AddAccountForm addAccountForm) {
        Boolean insert = false;
        try {
            //check exit Employee
            String checkEmployeeExit = accountManagerRepository.checkEmplyeeInAccountExit(addAccountForm.getEmployee());
            // check username gender
            if (checkEmployeeExit == null || checkEmployeeExit.isEmpty()) {
                String generateUserByIdEmployee = this.GenerateUsername(addAccountForm.getEmployee());
                if (generateUserByIdEmployee.equals(addAccountForm.getUsername())) {
                    Account ac = new Account();
                    ac.setPassword(passwordEncoder.encode(addAccountForm.getPassword()));
                    ac.setUsername(addAccountForm.getUsername());
                    AccountSercurity accountSercurity = new AccountSercurity();
                    ac.setCreated_date(LocalDate.now());
                    ac.setCreated_by(accountSercurity.getUserName());
                    List<Role> listRole = new ArrayList<>();
                    for (Long i : addAccountForm.getListRole()) {
                        Role role = new Role();
                        role.setId(i);
                        listRole.add(role);
                    }
                    ac.setRoles(listRole);
                    Employee em = new Employee();
                    em.setId(addAccountForm.getEmployee());
                    ac.setEmployee(em);
                    accountManagerRepository.save(ac);
                    insert = true;
                } else {
                    throw new Exception("Username not correct!");
                }
            } else {
                throw new Exception("Employee has account exit!");
            }
        }catch (Exception e){
            e.getMessage();
        }

        return insert;
    }

    @Override
    public Boolean updateAccount(UpdateAccountForm updateAccountForm)  {
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Account ac = accountManagerRepository.findAccountById(updateAccountForm.getId());
            if(ac.getUsername().equals(accountSercurity.getUserName())){
                throw new Exception("Account is available");
            }
            if(ac!=null){
                ac.setStatus(updateAccountForm.getStatus());
                ac.setModified_by(accountSercurity.getUserName());
                ac.setModified_date(LocalDate.now());
                ac.setModified_by(accountSercurity.getUserName());
                List<Role> listRole = new ArrayList<>();
                for(Long i : updateAccountForm.getListRole()){
                    Role role = new Role();
                    role.setId(i);
                    listRole.add(role);
                }
                ac.setRoles(listRole);
                accountManagerRepository.save(ac);
                return true;
            }
        }catch (Exception e){
            e.getMessage();
        }
        return false;
    }

    @Override
    public Boolean deleteAccount(Long idAccount) {
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Account account = accountManagerRepository.findAccountById(idAccount);
            if(account.getUsername().equals(accountSercurity.getUserName())){
                throw new Exception("Account is available");
            }
            account.setModified_by(accountSercurity.getUserName());
            account.setModified_date(LocalDate.now());
            account.setDeleted(true);
            accountManagerRepository.save(account);
            return true;
        }catch (Exception e){
            e.getMessage();
        }
        return false;
    }

    @Override 
    public List<GetAllAccountResponseVO> searchAccount(SearchAccountForm searchAccountForm) {
        List<GetAllAccountVO> listAcc = accountCustomRepository.getAllAccount(searchAccountForm);
        List<GetAllAccountResponseVO> result = new ArrayList<>();
        GetAllAccountResponseVO accountResponseVO = new GetAllAccountResponseVO();
        for(int i = 0 ; i< listAcc.size() ; i++){
            if(!listAcc.get(i).getId().equals(accountResponseVO.getId())){
                if(i!=0){
                    result.add(accountResponseVO);
                }
                accountResponseVO = new GetAllAccountResponseVO();
                accountResponseVO.setId(listAcc.get(i).getId());
                accountResponseVO.setStatus(listAcc.get(i).getStatus());
                accountResponseVO.setTime(listAcc.get(i).getTime());
                accountResponseVO.setUsername(listAcc.get(i).getUsername());
                if(listAcc.get(i).getIdRole()!=null){
                    accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(),listAcc.get(i).getNameRole()));
                }
            }else{
                if(listAcc.get(i).getIdRole()!=null){
                    accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(),listAcc.get(i).getNameRole()));
                }
            }
        }
        if(!accountResponseVO.getRoles().isEmpty()){
            result.add(accountResponseVO);
        }
        return result;
    }

    @Override
    public int getTotalSearchAccount(SearchAccountForm searchAccountForm) {
        if(searchAccountForm.getTotal() != null && searchAccountForm.getTotal().intValue() != 0){
            return searchAccountForm.getTotal();
        }
        return  accountCustomRepository.getTotalAllAccount(searchAccountForm);
    }

    @Override
    public GetAccountDetailResponeVO getAccountById(Long id) {
        try{
            List<GetAccountDetailVO> ac = accountManagerRepository.getAccountById(id);
            GetAccountDetailResponeVO result = new GetAccountDetailResponeVO();
            List<RoleAccountVO> listRole = new ArrayList<>();
            for(GetAccountDetailVO acd : ac){
               RoleAccountVO role = new RoleAccountVO();
               role.setId(acd.getIdRole());
               role.setName(acd.getNameRole());
               listRole.add(role);
            }
            result.setId(ac.get(0).getId());
            result.setUsername(ac.get(0).getUsername());
            result.setFullname(ac.get(0).getFullname());
            result.setImage(ac.get(0).getImage());
            result.setGender(ac.get(0).getGender());
            result.setDob(ac.get(0).getDob());
            result.setPhone(ac.get(0).getPhone());
            result.setRoles(listRole);
            return result;
        }catch (Exception e){
            e.getMessage();
        }
        return null;

    }

    @Override
    public String GenerateUsername(Long id) {
        try{
                String fullname = employeeRepository.getFullNameById(id);
                if(fullname !=null && !fullname.isEmpty()) {
                    AddAccountValidate addAccountValidate = new AddAccountValidate();
                    String usernameGen = addAccountValidate.generateFormatUsernameByFullname(fullname);
                    List<String> list = accountManagerRepository.getAllUsernameIsLike(usernameGen);
                    Integer number = addAccountValidate.genNumberUsername(usernameGen, list);
                    return usernameGen + ((number == 0) ? "" : number);
                }
        }catch (Exception e){
            throw new RuntimeException("Username not correct!");
        }
        return null;
    }


}
