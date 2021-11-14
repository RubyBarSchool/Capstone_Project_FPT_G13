package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.MaterialVO;
import com.university.fpt.acf.vo.SuggestMaterialVO;

import java.util.List;

public interface MaterialService {
    List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom);
    List<MaterialVO> searchMaterial(SearchMaterialForm searchForm);
    int totalSearchMaterial(SearchMaterialForm searchForm);
    List<MaterialVO> searchCoverSheet(SearchMaterialForm searchForm);
    int totalSearchCoverSheet(SearchMaterialForm searchForm);
    Boolean addMaterial(AddMaterialForm addForm);
    Boolean deleteMaterial(Long id);
    Boolean updateMaterial(UpdateMaterialForm updateForm);
    Boolean addCoverSheet(AddMaterialForm addForm);
    Boolean deleteCoverSheet(Long id);
    Boolean updateCoverSheet(UpdateMaterialForm updateForm);
    Boolean addUnitInMaterial(AddUnitFrameHeightForm addForm);
    Boolean addUnitInCoverSheet(AddUnitFrameHeightForm addForm);
}
