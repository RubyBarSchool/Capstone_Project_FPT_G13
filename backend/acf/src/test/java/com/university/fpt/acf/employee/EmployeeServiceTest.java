package com.university.fpt.acf.employee;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.service.impl.EmployeeServiceImpl;
import com.university.fpt.acf.vo.EmployeeDetailVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;
import com.university.fpt.acf.vo.SearchEmployeeVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;


@SpringBootTest
class EmployeeServiceTest {
    @Autowired
    private EmployeeServiceImpl underTest;

    @Test
    @Disabled
    void searchEmployeeDelete() {
        //given
        SearchAllEmployeeForm searchAllEmployeeForm = new SearchAllEmployeeForm();
        searchAllEmployeeForm.setName("a");
        searchAllEmployeeForm.setStatusDelete(true);
        searchAllEmployeeForm.setPageIndex(1);
        searchAllEmployeeForm.setPageSize(10);
        //when
        List<SearchEmployeeVO> listExpected = underTest.searchEmployee(searchAllEmployeeForm);
        //then
        assertThat(listExpected.size()==0);

    }
    @Test
    @Disabled
    void searchEmployee() {
        //given
        SearchAllEmployeeForm searchAllEmployeeForm = new SearchAllEmployeeForm();
        searchAllEmployeeForm.setName("a");
        searchAllEmployeeForm.setStatusDelete(false);
        searchAllEmployeeForm.setPageIndex(1);
        searchAllEmployeeForm.setPageSize(10);
        //when
        List<SearchEmployeeVO> listExpected = underTest.searchEmployee(searchAllEmployeeForm);
        //then
        assertThat(listExpected.size()==10);

    }

    @Test
    @Disabled
    void getTotalEmployee() {
        //given
        SearchAllEmployeeForm searchAllEmployeeForm = new SearchAllEmployeeForm();
        searchAllEmployeeForm.setName("a");
        searchAllEmployeeForm.setStatusDelete(false);
        searchAllEmployeeForm.setPageIndex(1);
        searchAllEmployeeForm.setPageSize(10);
        //when
        Integer expected = underTest.getTotalEmployee(searchAllEmployeeForm);
        //then
        assertThat(expected==10);
    }
    @Test
    @Disabled
    void getTotalEmployeeDelete() {
        //given
        SearchAllEmployeeForm searchAllEmployeeForm = new SearchAllEmployeeForm();
        searchAllEmployeeForm.setName("a");
        searchAllEmployeeForm.setStatusDelete(true);
        searchAllEmployeeForm.setPageIndex(1);
        searchAllEmployeeForm.setPageSize(10);
        //when
        Integer expected = underTest.getTotalEmployee(searchAllEmployeeForm);
        //then
        assertThat(expected==0);
    }

    @Test
    @Disabled
    void getFullNameEmployeeNotAccount() {
        //given
        SearchEmployeeForm searchEmployeeForm = new SearchEmployeeForm();
        searchEmployeeForm.setName("");
        searchEmployeeForm.setPageIndex(1);
        searchEmployeeForm.setPageSize(10);
        //when
        List<GetAllEmployeeVO> listExpected = underTest.getFullNameEmployeeNotAccount(searchEmployeeForm);
        //then
        assertThat(listExpected.size()==0);
    }

    @Test
    @Disabled
    void getAllEmployeeNotAttendance() {
        //given
        EmployeeNotAttendanceForm employeeNotAttendanceForm = new EmployeeNotAttendanceForm();
        employeeNotAttendanceForm.setDate(LocalDate.now());
        employeeNotAttendanceForm.setPageIndex(1);
        employeeNotAttendanceForm.setPageSize(10);
        //when
        List<GetAllEmployeeVO> listExpected = underTest.getAllEmployeeNotAttendance(employeeNotAttendanceForm);
        //then
        assertThat(listExpected.size()==10);
    }

    @Test
    @Disabled
    void getTotalEmployeeNotAttendance() {
        //given
        EmployeeNotAttendanceForm employeeNotAttendanceForm = new EmployeeNotAttendanceForm();
        employeeNotAttendanceForm.setDate(LocalDate.now());
        employeeNotAttendanceForm.setPageIndex(1);
        employeeNotAttendanceForm.setPageSize(10);
        //when
        Integer expected = underTest.getTotalEmployeeNotAttendance(employeeNotAttendanceForm);
        //then
        assertThat(expected==10);
    }

    @Test
    @Disabled
    void getEmployeeDetailById() {
        //given
        Long id = 1l;
        //when
        EmployeeDetailVO expected = underTest.getEmployeeDetailById(id);
        //then
        assertThat(expected!=null);

    }

    @Test
    @Disabled
    void addEmployee() {
        //given
        AddEmployeeForm addEmployeeForm = new AddEmployeeForm();
        addEmployeeForm.setFullName("Lê Anh Đạt");
        addEmployeeForm.setAddress("Kim Quan - Hà Nội");
        addEmployeeForm.setGender(true);
        addEmployeeForm.setEmail("ngoclthe131028@fpt.edu.vn");
        addEmployeeForm.setPhone("0961352582");
        addEmployeeForm.setNation("Kinh");
        addEmployeeForm.setIdPosition(2l);
        addEmployeeForm.setSalary(12000000l);
        LocalDate dob = LocalDate.of(1975, 11, 15);
        addEmployeeForm.setDob(dob);
        //when
        Boolean expected = underTest.AddEmployee(addEmployeeForm);
        //then
        assertThat(expected).isTrue();

    }

    @Test
    @Disabled
    void updateEmployee() {
        UpdateEmployeeForm updateEmployeeForm = new UpdateEmployeeForm();
        updateEmployeeForm.setId(327l);
        updateEmployeeForm.setFullName("Lê Anh Đạt");
        updateEmployeeForm.setAddress("Thạch Thất - Hà Nội");
        updateEmployeeForm.setGender(true);
        updateEmployeeForm.setEmail("ngoclthe131028@fpt.edu.vn");
        updateEmployeeForm.setPhone("0961352582");
        updateEmployeeForm.setNation("Kinh");
        updateEmployeeForm.setIdPosition(2l);
        updateEmployeeForm.setSalary(12500000l);
        LocalDate dob = LocalDate.of(1975, 11, 15);
        updateEmployeeForm.setDob(dob);
        //when
        Boolean expected = underTest.UpdateEmployee(updateEmployeeForm);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    @Disabled
    void deleteEmployee() {
        //given
        Long id = 327l;
        //when
        Boolean expected = underTest.DeleteEmployee(id);
        //then
        assertThat(expected).isTrue();
    }

    @Test

    void getEmployeeSNotDelete() {
        //given
        SearchEmployeeForm searchEmployeeForm = new SearchEmployeeForm();
        searchEmployeeForm.setName("a");
        searchEmployeeForm.setPageIndex(2);
        searchEmployeeForm.setPageSize(10);
        // when
        List<GetAllEmployeeVO> expectedList = underTest.getEmployeeSNotDelete(searchEmployeeForm);
        //then
        assertThat(expectedList.size()==10);
    }
}