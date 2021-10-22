package com.university.fpt.acf.repository;

import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PositionCustomRepository {
    List<PositionResponseVO> seachPosition(PositionForm positionForm);
    int totalSearchPosition(PositionForm positionForm);
}
