package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchAllFrame;
import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.vo.FrameMaterialVO;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;

import java.util.List;

public interface FrameMaterialCustomRepository {

    List<SearchFrameMaterialVO> searchFrame(SearchFrameMaterialForm searchForm);
    List<FrameMaterialVO> searchAllFrame(SearchAllFrame searchForm);
    int totalSearchFrame(SearchFrameMaterialForm searchForm);
    int totalsearchAllFrame(SearchAllFrame searchForm);
}
