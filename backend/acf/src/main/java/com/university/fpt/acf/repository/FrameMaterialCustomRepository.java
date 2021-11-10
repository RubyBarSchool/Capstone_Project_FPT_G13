package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;

import java.util.List;

public interface FrameMaterialCustomRepository {
    List<HeightMaterialVO>getAllHeightNotFrameTable(SearchHeightMaterialForm searchForm);
    List<SearchFrameMaterialVO> searchFrame(SearchFrameMaterialForm searchForm);
    int totalSearchFrame(SearchFrameMaterialForm searchForm);
}
