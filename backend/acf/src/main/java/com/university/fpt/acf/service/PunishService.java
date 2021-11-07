package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.UpdateBonusForm;
import com.university.fpt.acf.vo.ResultSearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface PunishService {
    List<ResultSearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchBonus);
    int totalSearchPunish(SearchBonusAdminForm searchBonus);
    List<SearchBonusAdminVO> searchPunishUser(BonusPunishForm bonusPunishForm);
    int totalSearchPunishUser(BonusPunishForm bonusPunishForm);
    Boolean addPunish(AddBonusAdminForm addBonus);
    Boolean deletePunish(Long id);
    Boolean updatePunish(UpdateBonusForm updateForm);
}
