package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.AccountManagerRepository;
import com.university.fpt.acf.service.AccountManagerService;
import com.university.fpt.acf.vo.GetAllAccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountManagerServiceImpl implements AccountManagerService {
    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<GetAllAccountVO> getAllAccounts(GetAllAccountForm getAllAccountForm) {
        Page<Account> pageListAccount = accountManagerRepository.findAll(PageRequest.of(getAllAccountForm.getPageIndex()-1,getAllAccountForm.getPageSize()));
        List<GetAllAccountVO> accounts = new ArrayList<>();
        for(Account i : pageListAccount){
            GetAllAccountVO accountVO = new GetAllAccountVO();
            accountVO.setId(i.getId());
            System.out.println("idAccount"+ accountVO.getId());
            accountVO.setUsername(i.getUsername());
            accountVO.setPassword(i.getPassword());
            accountVO.setRoles(i.getRoles());
            accountVO.setStatus(i.getStatus());
            accountVO.setTime(i.getModified_date());
            accounts.add(accountVO);

        }
        return accounts;
    }

    @Override
    public Boolean insertAccount(AddAccountForm addAccountForm) {
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

    @Override
    public Boolean updateAccount(UpdateAccountForm updateAccountForm) {
        Optional<Account> ac = accountManagerRepository.findById(updateAccountForm.getIdAccount());
        if(!ac.isEmpty()){
            Account acc = new Account();
            acc.setId(updateAccountForm.getIdAccount());
            acc.setPassword(passwordEncoder.encode(updateAccountForm.getPassword()));
            acc.setUsername(updateAccountForm.getUsername());
            AccountSercurity accountSercurity = new AccountSercurity();
            acc.setModified_by(accountSercurity.getUserName());
            acc.setCreated_by(accountSercurity.getUserName());
            List<Role> listRole = new ArrayList<>();
            for(Long i : updateAccountForm.getIdRole()){
                Role role = new Role();
                role.setId(i);
                listRole.add(role);
            }
            acc.setRoles(listRole);
            Employee em = new Employee();
            em.setId(updateAccountForm.getId_employee());
            acc.setEmployee(em);
            accountManagerRepository.save(acc);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteAccount(Long idAccount) {
        Optional<Account> ac = accountManagerRepository.findById(idAccount);
        Account account = ac.get();
        account.setDeleted(true);
        accountManagerRepository.save(account);
        return true;
    }

    @Override 
    public List<GetAllAccountVO> searchAccount(SearchAccountForm searchAccountForm) {
        Pageable pageable = PageRequest.of(searchAccountForm.getPageIndex()-1,searchAccountForm.getPageSize());
        List<Account> accounts =accountManagerRepository.findByUsernameIsLike("%"+searchAccountForm.getName()+"%",pageable);
        List<GetAllAccountVO> getAllAccountVOS = new ArrayList<>();
        for(Account i : accounts){
            GetAllAccountVO getAllAccountVO = new GetAllAccountVO();
            getAllAccountVO.setId(i.getId());
            getAllAccountVO.setUsername(i.getUsername());
            getAllAccountVO.setPassword(i.getPassword());
            getAllAccountVO.setRoles(i.getRoles());
            getAllAccountVO.setStatus(i.getStatus());
            getAllAccountVO.setTime(i.getModified_date());
            getAllAccountVOS.add(getAllAccountVO);
        }
        return getAllAccountVOS;
    }


}
