package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountManagerRepository extends JpaRepository<Account,Long> {
}
