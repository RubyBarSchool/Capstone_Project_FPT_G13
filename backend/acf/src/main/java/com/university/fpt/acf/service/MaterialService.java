package com.university.fpt.acf.service;

import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.vo.SuggestMaterialVO;

import java.util.List;

public interface MaterialService {
    List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom);
}
