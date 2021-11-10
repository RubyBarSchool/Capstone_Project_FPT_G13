package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.vo.SuggestMaterialVO;

import java.util.List;

public interface MaterialCustomRepository {
    List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom);
}
