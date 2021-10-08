package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.vo.GetAccountDetailVO;
import com.university.fpt.acf.vo.GetAllAccountVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountManagerRepository extends JpaRepository<Account,Long> {
    @Query("select new com.university.fpt.acf.vo.GetAccountDetailVO(a.id,a.username,r.id,r.name,e.image,e.fullName,e.dob,e.phone,e.gender) from Account a " +
            " inner join a.employee e " +
            "inner join a.roles r where a.id = ?1")
    List<GetAccountDetailVO> getAccountById(Long id);
    List<Account> findByUsernameIsLike(String username, Pageable pageable);
    Account findAccountById(Long id);
    Account findAccountByUsername(String userName);

    @Query("select new com.university.fpt.acf.vo.GetAllAccountVO(a.id,a.username,r.id,r.name,a.status,a.modified_date)  from Account a left join a.roles r")
    List<GetAllAccountVO> getAllAccount(Pageable pageable);

}
