package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddBonusAdminForm;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.UpdateBonusForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface BonusService {
    List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchBonus);
    int totalSearchBonus(SearchBonusAdminForm searchBonus);
    List<SearchBonusAdminVO> searchBonusUser(BonusPunishForm bonusPunishForm);
    int totalSearchBonusUser(BonusPunishForm bonusPunishForm);
    Boolean addBonus(AddBonusAdminForm addBonus);
    Boolean deleteBonus(Long id);
    Boolean updateBonus(UpdateBonusForm updateBonus);
}
