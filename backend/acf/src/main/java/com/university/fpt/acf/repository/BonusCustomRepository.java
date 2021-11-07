package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAndPunishVO;

import java.util.List;

public interface BonusCustomRepository {
    List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchForm);
    int totalSearchBonus(SearchBonusAdminForm searchForm);

    List<SearchBonusAndPunishVO> searchBonusAndPunish(SearchBonusAdminForm searchForm);
    int totalSearchBonusAndPunish(SearchBonusAdminForm searchForm);

    List<SearchBonusAdminVO> searchBonusUser(String username, BonusPunishForm bonusPunishForm);
    int totalSearchBonusUser(String username,BonusPunishForm bonusPunishForm);

}
