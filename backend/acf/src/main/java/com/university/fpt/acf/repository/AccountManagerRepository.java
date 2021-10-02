package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountManagerRepository extends JpaRepository<Account,Long> {

    List<Account> findByUsername(String username, Pageable pageable);

}
