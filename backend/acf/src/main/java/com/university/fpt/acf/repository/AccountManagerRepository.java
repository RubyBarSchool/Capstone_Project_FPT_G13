package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.vo.GetAccountDetailVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountManagerRepository extends JpaRepository<Account,Long> {
    @Query("select a.username,a.id,e.fullName,e.phone,e.dob,e.gender from Employee e " +
            "inner join Account a on e.id = a.employee.id where a.id =?1")
    GetAccountDetailVO getAccountById(Long id);
    List<Account> findByUsernameIsLike(String username, Pageable pageable);
    Account findAccountByUsername(String userName);

}
