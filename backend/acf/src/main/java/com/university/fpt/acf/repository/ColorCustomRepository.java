package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.SearchColorForm;
import com.university.fpt.acf.vo.ColorVO;

import java.util.List;

public interface ColorCustomRepository {
    List<ColorVO> searchColor(SearchColorForm searchColorForm);
    int getTotalSearchColor(SearchColorForm searchColorForm);
}
