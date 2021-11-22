package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.ResultSearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAndPunishVO;

import java.util.List;

public interface BonusService {
    List<ResultSearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchBonus);
    int totalSearchBonus(SearchBonusAdminForm searchBonus);
    List<SearchBonusAndPunishVO> searchBonusAndPunish(SearchBonusAndPunishForm searchBonusAndPunishForm);
    int totalSearchBonusAndPunish(SearchBonusAndPunishForm searchBonusAndPunishForm);

    List<SearchBonusAdminVO> searchBonusUser(BonusPunishForm bonusPunishForm);
    int totalSearchBonusUser(BonusPunishForm bonusPunishForm);
    Boolean addBonus(AddBonusAdminForm addBonus);
    Boolean deleteBonus(Long id);
    Boolean updateBonus(UpdateBonusForm updateBonus);
}
