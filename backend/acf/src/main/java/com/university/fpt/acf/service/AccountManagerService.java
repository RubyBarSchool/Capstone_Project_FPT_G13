package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.*;

import java.util.List;

public interface AccountManagerService {
    Boolean insertAccount(AddAccountForm addAccountForm);
    Boolean updateAccount(UpdateAccountForm updateAccountForm);
    Boolean deleteAccount(Long idAccount);
    List<GetAllAccountResponseVO> searchAccount(SearchAccountForm searchAccountForm);
    int getTotalSearchAccount(SearchAccountForm searchAccountForm);
    GetAccountDetailResponeVO getAccountById(Long id);
    String GenerateUsername(Long id);
    Boolean resetPassword(Long id);
    Boolean changePassword(ChangePasswordAccountForm  changePasswordAccountForm);
}
