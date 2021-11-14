package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddFrameMaterialForm;
import com.university.fpt.acf.form.SearchAllFrame;
import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.vo.FrameMaterialVO;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;

import java.util.List;

public interface FrameMaterialService {
    Boolean addFrame(AddFrameMaterialForm addForm);
    List<SearchFrameMaterialVO>searchFrame(SearchFrameMaterialForm searchForm);
    int totalSearch(SearchFrameMaterialForm searchForm);
    Boolean deleteFrame(Long id);
    List<FrameMaterialVO> getFrameCoverSheetToInsert();
    List<FrameMaterialVO>  getFrameMaterialToInsert();
    List<FrameMaterialVO> searchAllFrame(SearchAllFrame searchForm);
    int totalsearchAllFrame(SearchAllFrame searchForm);
}
