package com.university.fpt.acf.service;

import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.GetAllAccountVO;
import com.university.fpt.acf.vo.GetAllEmployeeVO;

import java.util.List;

public interface AccountManagerService {
    List<GetAllAccountVO> getAllAccounts(GetAllAccountForm getAllAccountForm);
    Boolean insertAccount(AddAccountForm addAccountForm);
    Boolean updateAccount(UpdateAccountForm updateAccountForm);
    Boolean deleteAccount(Long idAccount);
    List<GetAllAccountVO> getAllAccoutsBySearch(SearchAccountForm searchAccountForm);
}
