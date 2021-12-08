package com.university.fpt.acf.account;

import com.university.fpt.acf.form.AddAccountForm;
import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.form.UpdateAccountForm;
import com.university.fpt.acf.service.impl.AccountManagerServiceImpl;
import com.university.fpt.acf.vo.GetAccountDetailResponeVO;
import com.university.fpt.acf.vo.GetAllAccountResponseVO;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class AccountManagerServiceTest {
    @Autowired
    private AccountManagerServiceImpl underTest;

    @Test
    @Disabled
    void insertAccount() {
        //given
        AddAccountForm addAccountForm = new AddAccountForm();
        addAccountForm.setUsername("binhtv");
        addAccountForm.setEmployee(323l);
        List<Long> list = new ArrayList<>();
        list.add(3l);
        addAccountForm.setListRole(list);
        //when
        Boolean expected = underTest.insertAccount(addAccountForm);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    @Disabled
    void updateAccount() {
        //given
        UpdateAccountForm updateAccountForm = new UpdateAccountForm();
        List<Long> list = new ArrayList<>();
        list.add(2l);
        updateAccountForm.setListRole(list);
        updateAccountForm.setId(325l);
        updateAccountForm.setStatus(true);
        //when
        Boolean expected = underTest.updateAccount(updateAccountForm);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    @Disabled
    void deleteAccount() {
        //given
        Long id = 325l;
        //when
        Boolean expected = underTest.deleteAccount(id);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    @Disabled
    void searchAccount() {
        //given
        SearchAccountForm searchAccountForm = new SearchAccountForm();
        searchAccountForm.setName("truong");
        List<Long> list = new ArrayList<>();
        searchAccountForm.setListRole(list);
        List<LocalDate> dateList = new ArrayList<>();
        searchAccountForm.setDate(dateList);
        searchAccountForm.setPageIndex(1);
        searchAccountForm.setPageSize(10);
        //when
        List<GetAllAccountResponseVO> expectedList = underTest.searchAccount(searchAccountForm);
        //then
        assertThat(expectedList.size()==6).isTrue();
    }

    @Test
    @Disabled
    void getTotalSearchAccount() {
        //given
        SearchAccountForm searchAccountForm = new SearchAccountForm();
        searchAccountForm.setName("truong");
        List<Long> list = new ArrayList<>();
        searchAccountForm.setListRole(list);
        List<LocalDate> dateList = new ArrayList<>();
        searchAccountForm.setDate(dateList);
        searchAccountForm.setPageIndex(1);
        searchAccountForm.setPageSize(10);
        //when
        Integer expected = underTest.getTotalSearchAccount(searchAccountForm);
        //then
        assertThat(expected==6).isTrue();
    }

    @Test
    @Disabled
    void getAccountById() {
        //given
        Long id =1l;
        //when
        GetAccountDetailResponeVO expected = underTest.getAccountById(id);
        //then
        assertThat(expected!=null);
    }

    @Test
    @Disabled
    void generateUsername() {
        //given
        String username="truongtv";
        //when
        String expected = underTest.GenerateUsername(1l);
        //then
        assertThat(expected.equals(username));
    }

    @Test
    @Disabled
    void resetPassword() {
        //given
        Long id =118l;
        //when
        Boolean expected = underTest.resetPassword(id);
        //then
        assertThat(expected).isTrue();
    }
}