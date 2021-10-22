package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddPositionForm;
import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.form.UpdatePositionForm;
import com.university.fpt.acf.vo.PositionResponseVO;

import java.util.List;

public interface PositionService {
    List<PositionResponseVO> searchPosition(PositionForm positionForm);
    int totalSearchPosition(PositionForm positionForm);
    Boolean addPosition(AddPositionForm addPositionForm);
    Boolean updatePosition(UpdatePositionForm updatePositionForm);
    Boolean deletePosition(Long id);


}
