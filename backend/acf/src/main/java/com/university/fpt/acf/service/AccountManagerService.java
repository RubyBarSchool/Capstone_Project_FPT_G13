package com.university.fpt.acf.service;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.*;

import java.util.List;

public interface AccountManagerService {
    List<GetAllAccountResponseVO> getAllAccounts(GetAllAccountForm getAllAccountForm);
    Boolean insertAccount(AddAccountForm addAccountForm);
    Boolean updateAccount(UpdateAccountForm updateAccountForm);
    Boolean deleteAccount(Long idAccount);
    List<GetAllAccountResponseVO> searchAccount(SearchAccountForm searchAccountForm);
    GetAccountDetailResponeVO getAccountById(Long id);
    String GenerateUsername(String fullName);
}
