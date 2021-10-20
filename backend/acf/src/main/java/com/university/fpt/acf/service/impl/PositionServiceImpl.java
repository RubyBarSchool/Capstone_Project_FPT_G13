package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.repository.PositionCustomRepository;
import com.university.fpt.acf.repository.PositionRespository;
import com.university.fpt.acf.service.PositionService;
import com.university.fpt.acf.vo.GetAllRoleVO;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRespository positionRespository;
    @Autowired
    private PositionCustomRepository positionCustomRepository;

    @Override
    public List<PositionResponseVO> searchPosition(PositionForm positionForm) {
        List<PositionResponseVO> getAlPositionVOS = new ArrayList<>();
        try {
            getAlPositionVOS = positionCustomRepository.seachPosition(positionForm);
        } catch (Exception e) {
            throw new RuntimeException("Error position repository " + e.getMessage());
        }
        return  getAlPositionVOS;
    }
}
