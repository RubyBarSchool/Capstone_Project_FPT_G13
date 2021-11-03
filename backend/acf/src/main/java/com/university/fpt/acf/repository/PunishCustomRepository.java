package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.vo.SearchBonusAdminVO;

import java.util.List;

public interface PunishCustomRepository {
    List<SearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchForm);
    int totalSearchPunish(SearchBonusAdminForm searchForm);
}
