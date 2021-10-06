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
//    @Query("select ac from Account as ac inner join Employee" +
//            " as em on ac.employee.id = em.id \n" +
//            "left join Position as p on em.position.id = p.id")
    GetAccountDetailVO getAccountById(Long id);
    List<Account> findByUsernameIsLike(String username, Pageable pageable);


}
