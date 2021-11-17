package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.CheckMaterialForm;
import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.vo.*;

import java.util.List;

public interface MaterialCustomRepository {
    List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom);
    List<MaterialVO> searchMaterial(SearchMaterialForm searchForm);
    int totalSearchMaterial(SearchMaterialForm searchForm);

    List<MaterialInContactDetailVO>searchMaterialInAddProduct(SearchMaterialForm searchForm);
    int totalSearchMaterialInAddProduct(SearchMaterialForm searchForm);

    List<MaterialVO> searchCoverSheet(SearchMaterialForm searchForm);
    int totalSearchCoverSheet(SearchMaterialForm searchForm);
    String getNameMaterial(CheckMaterialForm checkMaterialForm);
    String getNameCoverSheet(CheckMaterialForm checkMaterialForm);

}
