package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAndPunishForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAndPunishVO;

import java.util.List;

public interface BonusCustomRepository {
    List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchForm);
    int totalSearchBonus(SearchBonusAdminForm searchForm);

    List<SearchBonusAndPunishVO> searchBonusAndPunish(String userName,SearchBonusAndPunishForm searchBonusAndPunishForm);
    int totalSearchBonusAndPunish(String userName,SearchBonusAndPunishForm searchBonusAndPunishForm);

    List<SearchBonusAdminVO> searchBonusUser(String username, BonusPunishForm bonusPunishForm);
    int totalSearchBonusUser(String username,BonusPunishForm bonusPunishForm);

}
