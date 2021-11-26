package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Contact;
import com.university.fpt.acf.entity.ContactMoney;
import com.university.fpt.acf.vo.ContactVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactMoneyRepository extends JpaRepository<ContactMoney,Long> {
    @Query("select cm from ContactMoney cm where cm.deleted = false and cm.id = :id")
    ContactMoney getContactMoneyByID(@Param("id") Long id);

}
