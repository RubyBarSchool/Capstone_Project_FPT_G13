package com.university.fpt.acf.service;

import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;

import java.util.List;

public interface UnitMeasureService {
    Boolean addUnitMeasure(String name);
    Boolean deleteUnitMeasure(Long id);
    List<UnitMeasureVO> getAllUnit();
    List<UnitMeasureVO> getUnitsToInsertMaterial();
    List<UnitMeasureVO> getUnitsToInsertCoverInsert();
}
