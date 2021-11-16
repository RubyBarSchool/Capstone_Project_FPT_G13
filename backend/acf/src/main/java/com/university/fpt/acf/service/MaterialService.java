package com.university.fpt.acf.service;

import com.university.fpt.acf.form.*;
import com.university.fpt.acf.vo.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


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
    List<GetAllMaterialVO> getAllMaterial();
    List<GetAllMaterialVO> getAllCoverSheet();
    List<UnitMeasureVO>getUnitsByMaterial(Long id);
    List<UnitMeasureVO>getUnitsByCoverSheet(Long id);
    List<GetAllMaterialVO> getMaterialByUnit( Long id);
    List<GetAllMaterialVO> getCoverSheetByUnit(Long id);

    List<HeightMaterialVO>getHeightSByMaterialAndFrame(Long idMaterial, Long idFrame);
    List<HeightMaterialVO>getHeightByCoverSheetAndFrame(Long idMaterial,Long idFrame);
    List<GetAllMaterialVO> getMaterialByHeightFrame(Long idHeight,Long idFrame);
    List<GetAllMaterialVO> getCoverSheetByHeightFrame(Long idHeight, Long idFrame);
    List<FrameMaterialVO> getFrameByMaterialAndHeight(Long idHeight,Long idMaterial);
    List<FrameMaterialVO> getFrameByCoverSheetAndHeight(Long idHeight,Long idCoverSheet);

}
