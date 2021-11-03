package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface PunishService {
    List<SearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchBonus);
    int totalSearchPunish(SearchBonusAdminForm searchBonus);
    Boolean addPunish(AddBonusAdminForm addBonus);
    Boolean deletePunish(Long id);
}
