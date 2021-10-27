package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddColorForm;
import com.university.fpt.acf.form.SearchColorForm;
import com.university.fpt.acf.form.UpdateColorForm;
import com.university.fpt.acf.vo.ColorVO;

import java.util.List;

public interface ColorService {
    List<ColorVO> searchColor(SearchColorForm searchColorForm);
    int getTotalSearchColor(SearchColorForm searchColorForm);
    Boolean insertColor(AddColorForm addColorForm);
    Boolean updateColor(UpdateColorForm updateColorForm);
    Boolean deleteColor(Long id);
}
