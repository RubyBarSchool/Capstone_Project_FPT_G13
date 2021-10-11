package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.*;
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
import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class AccountManagerServiceImpl implements AccountManagerService {
    @Autowired
    private AccountManagerRepository accountManagerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<GetAllAccountResponseVO> getAllAccounts(GetAllAccountForm getAllAccountForm) {
            Pageable pageable= PageRequest.of(getAllAccountForm.getPageIndex()-1,getAllAccountForm.getPageSize(), Sort.by("id").ascending());
            List<GetAllAccountVO> listAcc = accountManagerRepository.getAllAccount(pageable);
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
                        accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(),listAcc.get(i).getNameRole()));
                }else{
                        accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(),listAcc.get(i).getNameRole()));
                }
            }
            if(!accountResponseVO.getRoles().isEmpty()){
                result.add(accountResponseVO);
            }
            return result;
    }

    @Override
    public Boolean insertAccount(AddAccountForm addAccountForm) {
        try {
            //check exit Account
            if(accountManagerRepository.findAccountByUsername(addAccountForm.getUsername()) == null){
                Account ac = new Account();
                ac.setPassword(passwordEncoder.encode(addAccountForm.getPassword()));
                ac.setUsername(addAccountForm.getUsername());
                AccountSercurity accountSercurity = new AccountSercurity();
                ac.setModified_by(accountSercurity.getUserName());
                ac.setCreated_by(accountSercurity.getUserName());
                List<Role> listRole = new ArrayList<>();
                for(Long i : addAccountForm.getIdRole()){
                    Role role = new Role();
                    role.setId(i);
                    listRole.add(role);
                }
                ac.setRoles(listRole);
                Employee em = new Employee();
                em.setId(addAccountForm.getId_employee());
                ac.setEmployee(em);
                accountManagerRepository.save(ac);
                return true;
            }
        }catch (Exception ex){
            ex.getMessage();
        }
        return false;
    }

    @Override
    public Boolean updateAccount(UpdateAccountForm updateAccountForm) {
        try{
            Account ac = accountManagerRepository.findAccountById(updateAccountForm.getIdAccount());
            if(ac!=null){
                ac.setStatus(updateAccountForm.getStatus());
                AccountSercurity accountSercurity = new AccountSercurity();
                ac.setModified_by(accountSercurity.getUserName());
                ac.setCreated_by(accountSercurity.getUserName());
                List<Role> listRole = new ArrayList<>();
                for(Long i : updateAccountForm.getIdRole()){
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
            Account account = accountManagerRepository.findAccountById(idAccount);
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
        Pageable pageable= PageRequest.of(searchAccountForm.getPageIndex()-1,searchAccountForm.getPageSize(), Sort.by("id").ascending());
        List<GetAllAccountVO> listAcc = accountManagerRepository.findByUsernameIsLike("%"+searchAccountForm.getName()+"%",pageable);
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
                accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(),listAcc.get(i).getNameRole()));
            }else{
                accountResponseVO.getRoles().add(new RoleAccountVO(listAcc.get(i).getIdRole(),listAcc.get(i).getNameRole()));
            }
        }
        if(!accountResponseVO.getRoles().isEmpty()){
            result.add(accountResponseVO);
        }
        return result;
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
    public String GenerateUsername(String fullname) {
        try{
            if(!fullname.isEmpty()){
                AddAccountValidate addAccountValidate = new AddAccountValidate();
                String usernameGen = addAccountValidate.generateFormatUsernameByFullname(fullname);
                List<String> list = accountManagerRepository.getAllUsernameIsLike(usernameGen);
                return usernameGen+ addAccountValidate.genNumberUsername(usernameGen,list);
            }
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }


}
