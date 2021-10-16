package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchAccountForm;
import com.university.fpt.acf.vo.GetAllAccountVO;

import java.util.List;

public interface AccountCustomRepository {
    List<GetAllAccountVO> getAllAccount(SearchAccountForm searchAccountForm);
    int getTotalAllAccount(SearchAccountForm searchAccountForm);
}
