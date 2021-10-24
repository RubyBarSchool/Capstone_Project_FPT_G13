package com.university.fpt.acf.config.security.repository;

import com.university.fpt.acf.config.security.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    @Query("select a from Account a where a.username = :username and a.deleted = false and a.status = true")
    Account findByUsername(@Param("username") String username);
}
