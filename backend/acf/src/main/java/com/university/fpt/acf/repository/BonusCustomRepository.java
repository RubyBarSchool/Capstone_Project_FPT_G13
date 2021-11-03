package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface BonusCustomRepository {
    List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchForm);
    int totalSearchBonus(SearchBonusAdminForm searchForm);
}
