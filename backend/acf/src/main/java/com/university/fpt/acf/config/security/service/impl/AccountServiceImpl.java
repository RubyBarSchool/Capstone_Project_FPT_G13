package com.university.fpt.acf.config.security.service.impl;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.config.security.repository.AccountRepository;
import com.university.fpt.acf.config.security.repository.RoleRepository;
import com.university.fpt.acf.config.security.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account saveAccount(Account account) {
        log.info("Saving new user to database");
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    @Override
    public void addRoleToAccount(String username, String roleName) {
        log.info("Adding role {} to user {}",roleName,username);
        Account user = accountRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public Account getAccount(String username) {
        log.info("Fetching user {}",username);
        return accountRepository.findByUsername(username);
    }

    @Override
    public List<Account> getAccounts() {
        log.info("Fetching all users");
        return accountRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(s);
        if(account == null ){
            log.error("User not found in the database");
            throw  new UsernameNotFoundException("user not found in the database");
        }else{
            log.info("User found in the database {}",s);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        account.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getCode()));
        });
        return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);    }
}
