package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.form.AddPositionForm;
import com.university.fpt.acf.form.PositionForm;
import com.university.fpt.acf.form.UpdatePositionForm;
import com.university.fpt.acf.repository.PositionCustomRepository;
import com.university.fpt.acf.repository.PositionRespository;
import com.university.fpt.acf.service.PositionService;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    public int totalSearchPosition(PositionForm positionForm) {
        int total=0;
        try {
            total = positionCustomRepository.totalSearchPosition(positionForm);
        }catch (Exception e){
            e.getMessage();
        }
        return total;
    }

    @Override
    public Boolean addPosition(AddPositionForm addPositionForm) {
        boolean check = false;
        try{
            if(addPositionForm.getCode()==null && addPositionForm.getName()==null){
                throw new Exception("Data position is null");
            }else{
                if(positionRespository.checkExitPosition(addPositionForm.getName())==null){
                    Position p = new Position();
                    p.setName(addPositionForm.getName());
                    p.setCode(addPositionForm.getCode());
                    AccountSercurity accountSercurity = new AccountSercurity();
                    p.setModified_by(accountSercurity.getUserName());
                    p.setCreated_by(accountSercurity.getUserName());
                    positionRespository.save(p);
                    check =true;
                }else {
                    throw new Exception("Position is existed");
                }

            }

        }catch (Exception e){
            e.getMessage();
        }
        return check;
    }

    @Override
    public Boolean updatePosition(UpdatePositionForm updatePositionForm) {
        boolean check = false;
        try{
            if(updatePositionForm.getCode()==null && updatePositionForm.getName()==null){
                throw new Exception("Data update position is null");
            }else{
                if(positionRespository.checkDeletePositionById(updatePositionForm.getId())==null){
                       if(positionRespository.CheckExitPositionById(updatePositionForm.getId())!=null){
                           if(positionRespository.checkExitPosition(updatePositionForm.getName())== null ||
                           positionRespository.checkExitPosition(updatePositionForm.getName())== updatePositionForm.getName()){
                               Position p  = positionRespository.getPositionById(updatePositionForm.getId());
                               p.setName(updatePositionForm.getName());
                               p.setCode(updatePositionForm.getCode());
                               AccountSercurity accountSercurity = new AccountSercurity();
                               p.setModified_by(accountSercurity.getUserName());
                               positionRespository.save(p);
                               check =true;
                           }else{
                               throw new Exception("Position name is  existed");
                           }

                    }else{
                        throw new Exception("Position is not existed");
                    }
                }else {
                    throw new Exception("Position is deleted");
                }
            }
        }catch (Exception e){
            e.getMessage();
        }
        return check;
    }

    @Override
    public Boolean deletePosition(Long id) {
        boolean check = false;
        try{
            Position p = positionRespository.getPositionById(id);
            if(p != null){
                p.setDeleted(true);
                AccountSercurity accountSercurity = new AccountSercurity();
                p.setModified_by(accountSercurity.getUserName());
                positionRespository.save(p);
                check =true;
            }else {
                throw new Exception("Position is not existed");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return check;
    }
}
