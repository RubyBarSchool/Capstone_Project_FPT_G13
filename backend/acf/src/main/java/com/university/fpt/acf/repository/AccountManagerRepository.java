package com.university.fpt.acf.repository;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.vo.GetAccountDetailVO;
import com.university.fpt.acf.vo.GetAllAccountVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface AccountManagerRepository extends JpaRepository<Account,Long> {
    @Query("select new com.university.fpt.acf.vo.GetAccountDetailVO(a.id,a.username,r.id,r.name,e.image,e.fullName,e.dob,e.phone,e.gender) from Account a " +
            " inner join a.employee e " +
            "inner join a.roles r where a.id=:id and a.deleted = false ")
    List<GetAccountDetailVO> getAccountById(@Param("id") Long id);

    @Query("select new com.university.fpt.acf.vo.GetAllAccountVO(a.id,a.username,r.id,r.name,a.status,a.modified_date)  " +
            "from Account a left join a.roles r where a.username LIKE :username and a.deleted = false ")
    List<GetAllAccountVO> findByUsernameIsLike(@Param("username") String username, Pageable pageable);

    Account findAccountById(Long id);

    Account findAccountByUsername(String userName);

    @Query("select new com.university.fpt.acf.vo.GetAllAccountVO(a.id,a.username,r.id,r.name,a.status,a.modified_date)  " +
            "from Account a left join a.roles r where a.deleted = false  ")
    List<GetAllAccountVO> getAllAccount(Pageable pageable);

    @Query("select a.username from Account a where a.username like :username and a.deleted = false")
    List<String> getAllUsernameIsLike(@Param("username") String username);

    @Query("SELECT a.username FROM Account a where  a.employee.id = :id")
    String checkEmplyeeInAccountExit(@Param("id") Long id);

    @Query("SELECT DISTINCT e.email FROM Account a inner join  a.employee e  inner join a.roles r where a.deleted = false and e.deleted = false and r.deleted = false and r.id = 3")
    List<String> getEmailAdmin();
    @Query("SELECT e.id FROM Account a inner join  a.employee e  where a.deleted = false and e.deleted = false  and a.username=:username")
    Long getIdEmployeeByUsername(@Param("username") String username);
}
