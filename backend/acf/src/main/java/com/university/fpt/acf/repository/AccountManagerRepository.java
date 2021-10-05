package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.vo.GetAccountDetailVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountManagerRepository extends JpaRepository<Account,Long> {
    @Query("select * from acfcapstoneproject.account as ac inner join acfcapstoneproject.employee as em on ac.employee_id = em.id \n" +
            "left join acfcapstoneproject.position as p on em.position_id = p.id")
    GetAccountDetailVO getAccountById(Long id);
    List<Account> findByUsernameIsLike(String username, Pageable pageable);


}
