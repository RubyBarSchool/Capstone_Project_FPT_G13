package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface BonusService {
    List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchBonus);
    int totalSearchBonus(SearchBonusAdminForm searchBonus);
    Boolean addBonus(AddBonusAdminForm addBonus);
}
