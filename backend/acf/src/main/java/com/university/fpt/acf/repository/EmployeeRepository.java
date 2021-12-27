package com.university.fpt.acf.repository;

import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.vo.DetailEmployeeInformationVO;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    @Query("select e.fullName from Employee e " +
            " where e.id = :id and e.deleted = false ")
    String getFullNameById(@Param("id") Long id);

    @Query("select new com.university.fpt.acf.vo.GetAllEmployeeVO(e.id,e.fullName) from Employee e  left join Account a on e.id = a.employee.id  " +
            "where e.deleted = false and a.username is null and LOWER(e.fullName) LIKE :fullname order by e.fullName asc  ")
    List<GetAllEmployeeVO> getTop10EmployeeNotAccount(@Param("fullname") String fullName,Pageable pageable);

    @Query("SELECT new com.university.fpt.acf.vo.EmployeeDetailVO(img.name,e.fullName,e.gender,e.dob,e.email,e.phone,e.address,e.nation,e.salary,p.id,p.name)  " +
            "FROM Employee e left join  e.image img left join e.position p where e.id =:id")
    EmployeeDetailVO getEmployeeById(@Param("id") Long id);

    @Query("SELECT new com.university.fpt.acf.vo.EmployeeDetailVO(img.name,e.fullName,e.gender,e.dob,e.email,e.phone,e.address,e.nation,e.salary,p.id,p.name)  " +
            "FROM Account a inner  join  a.employee e left join  e.image img left join e.position p where a.username =:username")
    EmployeeDetailVO getEmployeeVOByUsername(@Param("username") String username);

    @Query("SELECT e.phone FROM Employee e where e.phone=:phone and e.deleted=false")
    String checkExitPhone(@Param("phone") String phone);

    @Query("SELECT e.email FROM Employee e where e.email=:email and e.deleted=false")
    String checkExitEmail(@Param("email") String email);

    @Query("SELECT e FROM Employee e left join e.image img where e.deleted = false and e.id=:id")
    Employee getEmployeeToUpdateById(@Param("id") Long id);


    @Query("SELECT e FROM Employee e where e.deleted = false and e.id  in :id")
    List<Employee> getEmployeeByIdS(@Param("id") List<Long> ids);
    @Query("SELECT new com.university.fpt.acf.vo.GetAllEmployeeVO(e.id,e.fullName) FROM Employee e where e.deleted = false and  LOWER(e.fullName) LIKE :fullname order by e.fullName asc")
    List<GetAllEmployeeVO> getEmployeeNotDelete(@Param("fullname") String fullName,Pageable pageable);

    @Query("SELECT e FROM Employee e where e.deleted = false ")
    List<Employee> getAllEmployee();


    @Query("SELECT e FROM Employee e inner join e.image img where img.name = :idFile or img.id = :idFile")
    Employee getEmployeeByFile(@Param("idFile") String image);

    @Query("SELECT e FROM Employee e inner join  e.position p where e.deleted = false and p.code = 'GD' ")
    List<Employee> getEmployeeGD();
    @Query("SELECT new com.university.fpt.acf.vo.DetailEmployeeInformationVO(img.name,e.fullName,e.gender,e.nation, e.dob,e.email,p.name,e.phone,e.address,e.salary,a.username,r.name) FROM Account a left  join a.employee e left join a.roles r left join e.image img left join e.position p where a.username=:username ")
    List<DetailEmployeeInformationVO> getDetailEmployee(@Param("username")String username);


    @Query("SELECT e FROM Account a inner join a.employee e  where a.username=:username and a.deleted = false  and e.deleted = false")
    Employee getEmployeeByUsername(@Param("username")String username);

    @Query("select COUNT(e.id)from Employee e  left join Account a on e.id = a.employee.id  " +
            "where e.deleted= false and a.username is null")
    Integer getEmployeeNotAccount();
}
