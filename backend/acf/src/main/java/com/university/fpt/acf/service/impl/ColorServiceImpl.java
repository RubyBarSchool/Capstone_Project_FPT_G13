package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.Color;
import com.university.fpt.acf.entity.Company;
import com.university.fpt.acf.entity.Position;
import com.university.fpt.acf.form.AddColorForm;
import com.university.fpt.acf.form.SearchColorForm;
import com.university.fpt.acf.form.UpdateColorForm;
import com.university.fpt.acf.repository.ColorCustomRepository;
import com.university.fpt.acf.repository.ColorRespository;
import com.university.fpt.acf.service.ColorService;
import com.university.fpt.acf.vo.ColorVO;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorCustomRepository colorCustomRepository;
    @Autowired
    private ColorRespository colorRespository;
    @Override
    public List<ColorVO> searchColor(SearchColorForm searchColorForm) {
        List<ColorVO> getAlColorVOS = new ArrayList<>();
        try {
            getAlColorVOS = colorCustomRepository.searchColor(searchColorForm);
        } catch (Exception e) {
            throw new RuntimeException("Error color repository " + e.getMessage());
        }
        return  getAlColorVOS;
    }

    @Override
    public int getTotalSearchColor(SearchColorForm searchColorForm) {
        int total=0;
        try {
            total = colorCustomRepository.getTotalSearchColor(searchColorForm);
        }catch (Exception e){
            e.getMessage();
        }
        return total;
    }

    @Override
    public Boolean insertColor(AddColorForm addColorForm) {
        boolean check = false;
        try{
            if(addColorForm.getCode()==null && addColorForm.getName()==null){
                throw new Exception("Data color is null");
            }else{
                if(colorRespository.checkExitCompany(addColorForm.getIdCompany())!=null&&
                        !colorRespository.checkExitCompany(addColorForm.getIdCompany()).isEmpty()){
                    Color c = new Color();
                    c.setName(addColorForm.getName());
                    c.setCode(addColorForm.getCode());
                    c.setImage(addColorForm.getImage());
                    Company cp = new Company();
                    cp.setId(addColorForm.getIdCompany());
                    c.setCompany(cp);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    c.setModified_by(accountSercurity.getUserName());
                    c.setCreated_by(accountSercurity.getUserName());
                    colorRespository.save(c);
                    check =true;
                }else {
                    throw new Exception("Company ko ton tai");
                }

            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean updateColor(UpdateColorForm updateColorForm) {
            boolean check = false;
            try{
                if(updateColorForm.getCode()==null && updateColorForm.getName()==null){
                    throw new Exception("Data color is null");
                }else{
                    if(colorRespository.checkExitCompany(updateColorForm.getIdCompany())!=null&&
                            !colorRespository.checkExitCompany(updateColorForm.getIdCompany()).isEmpty()){
                        Color c  = colorRespository.getColorById(updateColorForm.getId());
                        if(c!=null) {
                            c.setName(updateColorForm.getName());
                            c.setCode(updateColorForm.getCode());
                            c.setName(updateColorForm.getName());
                            c.setImage(updateColorForm.getImage());
                            Company cp = new Company();
                            cp.setId(updateColorForm.getIdCompany());
                            c.setCompany(cp);
                            AccountSercurity accountSercurity = new AccountSercurity();
                            c.setModified_by(accountSercurity.getUserName());
                            c.setModified_date(LocalDate.now());
                            check = true;
                            colorRespository.save(c);
                        }else {
                            throw new Exception("Color ko ton tai");
                        }


                    }else {
                        throw new Exception("Company ko ton tai");
                    }

                }

            }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }

    @Override
    public Boolean deleteColor(Long id) {
        boolean check = false;
        try{
                Color c  = colorRespository.getColorById(id);
                if(c!=null) {
                    c.setDeleted(true);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    c.setModified_by(accountSercurity.getUserName());
                    c.setModified_date(LocalDate.now());
                    check = true;
                    colorRespository.save(c);
                }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return check;
    }
}
