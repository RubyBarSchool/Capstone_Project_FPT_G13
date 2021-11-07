package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface PunishCustomRepository {
    List<SearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchForm);
    int totalSearchPunish(SearchBonusAdminForm searchForm);

    List<SearchBonusAdminVO> searchPunishUser(String username, BonusPunishForm bonusPunishForm);
    int totalSearchPunishUser(String username,BonusPunishForm bonusPunishForm);

}
