package com.university.fpt.acf.service;

import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.vo.PositionResponseVO;

import java.util.List;

public interface PositionService {
    List<PositionResponseVO> searchPosition(PositionForm positionForm);

}
