package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.security.entity.Account;
import com.university.fpt.acf.config.security.entity.Role;
import com.university.fpt.acf.entity.Employee;
import com.university.fpt.acf.entity.UnitMeasure;
import com.university.fpt.acf.repository.UnitMeasureRepository;
import com.university.fpt.acf.service.UnitMeasureService;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UnitMeasureServiceImpl implements UnitMeasureService {
    @Autowired
    private UnitMeasureRepository unitMeasureRepository;
    @Override
    //************************************
    // Add unit measure
    //************************************
    public Boolean addUnitMeasure(String name) {
        Boolean insert = false;
        try {
            if(unitMeasureRepository.getIdByName(name)!=null){
                throw new Exception("Đơn vị đã tồn tại");
            }
            UnitMeasure u = new UnitMeasure();
            u.setName(name);
            AccountSercurity accountSercurity = new AccountSercurity();
            u.setCreated_date(LocalDate.now());
            u.setCreated_by(accountSercurity.getUserName());
            u.setModified_by(accountSercurity.getUserName());
            unitMeasureRepository.save(u);
            insert = true;
        }catch (Exception e){
            e.getMessage();
        }
        return insert;
    }
    //************************************
    // Delete unit measure
    //************************************
    @Override
    public Boolean deleteUnitMeasure(Long id) {
        Boolean delete = false;
        try {
            UnitMeasure u = unitMeasureRepository.getUnitByID(id);
            if(u==null ){
                throw new Exception("Đơn vị không tồn tại");
            }
            u.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            u.setModified_date(LocalDate.now());
            u.setModified_by(accountSercurity.getUserName());
            unitMeasureRepository.save(u);
            delete = true;
        }catch (Exception e){
            e.getMessage();
        }
        return delete;
    }
    //************************************
    //GEt all units
    //************************************
    @Override
    public List<UnitMeasureVO> getAllUnit() {
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = unitMeasureRepository.getAllUnit();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
    //************************************
    // Get units to Insert material
    //************************************
    @Override
    public List<UnitMeasureVO> getUnitsToInsertMaterial() {
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = unitMeasureRepository.getUnitsMaterialToInsert();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
    //************************************
    // Get Units to Insert Cover Insert
    //************************************
    @Override
    public List<UnitMeasureVO> getUnitsToInsertCoverInsert() {
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = unitMeasureRepository.getUnitsCoverSheetToInsert();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
}