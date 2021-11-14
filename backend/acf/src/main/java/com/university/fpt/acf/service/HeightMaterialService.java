package com.university.fpt.acf.service;

import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;

import java.util.List;

public interface HeightMaterialService {
    Boolean addHeightMaterial(String frameHeight);
    Boolean deleteHeightMaterial(Long id);
    List<HeightMaterialVO> getAllHeights();
    List<HeightMaterialVO> getHeightsToInsertMaterial();
    List<HeightMaterialVO> getHeightsToInsertCoverInsert();
}
