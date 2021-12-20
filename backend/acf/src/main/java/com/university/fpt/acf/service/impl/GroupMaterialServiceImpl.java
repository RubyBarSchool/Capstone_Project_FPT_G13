package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.GroupMaterial;
import com.university.fpt.acf.entity.HeightMaterial;
import com.university.fpt.acf.repository.GroupMaterialRepository;
import com.university.fpt.acf.service.GroupMaterialService;
import com.university.fpt.acf.vo.GroupMaterialVO;
import com.university.fpt.acf.vo.HeightMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupMaterialServiceImpl implements GroupMaterialService {
    @Autowired
    private GroupMaterialRepository repository;
    //************************************
    // Add group material
    //************************************
    @Override
    public Boolean addGroupMaterial(String name) {
        Boolean insert = false;
        try {
            if(repository.getIdByNameGroup(name)!=null){
                throw new Exception("Nhóm vật liệu đã tồn tại");
            }
            GroupMaterial h = new GroupMaterial();
            h.setName(name);
            AccountSercurity accountSercurity = new AccountSercurity();
            h.setModified_by(accountSercurity.getUserName());
            h.setCreated_by(accountSercurity.getUserName());
            h.setCreated_date(LocalDate.now());
            repository.save(h);
            insert = true;
        }catch (Exception e){
            e.getMessage();
        }
        return insert;
    }
    //************************************
    // Delete group material
    //************************************
    @Override
    public Boolean deleteGroupMaterial(Long id) {
        Boolean delete = false;
        try {
            GroupMaterial h = repository.getGroupMaterialByID(id);
            if(h==null ){
                throw new Exception("Nhóm vật liệu không tồn tại");
            }
            AccountSercurity accountSercurity = new AccountSercurity();
            h.setModified_by(accountSercurity.getUserName());
            h.setModified_date(LocalDate.now());
            h.setDeleted(true);
            repository.save(h);
            delete = true;
        }catch (Exception e){
            e.getMessage();
        }
        return delete;
    }
    //************************************
    // GEt all group material
    //************************************
    @Override
    public List<GroupMaterialVO> getAllGroupMaterial() {
        List<GroupMaterialVO> list = new ArrayList<>();
        try {
            list = repository.getAllGroups();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
    //************************************
    // Add group coverplate
    //************************************
    @Override
    public Boolean addGroupCoverPlate(String name) {
        Boolean insert = false;
        try {
            if(repository.getIdCoverPlateByNameGroup(name)!=null){
                throw new Exception("Nhóm tấm phủ đã tồn tại");
            }
            GroupMaterial h = new GroupMaterial();
            h.setName(name);
            h.setCheckGroupMaterial(false);
            AccountSercurity accountSercurity = new AccountSercurity();
            h.setModified_by(accountSercurity.getUserName());
            h.setCreated_by(accountSercurity.getUserName());
            h.setCreated_date(LocalDate.now());
            repository.save(h);
            insert = true;
        }catch (Exception e){
            e.getMessage();
        }
        return insert;
    }
    //************************************
    // Delete group cover plate
    //************************************
    @Override
    public Boolean deleteGroupCoverPlate(Long id) {
        Boolean delete = false;
        try {
            GroupMaterial h = repository.getGroupCoverPlateByID(id);
            if(h==null ){
                throw new Exception("Nhóm tấm phủ không tồn tại");
            }
            AccountSercurity accountSercurity = new AccountSercurity();
            h.setModified_by(accountSercurity.getUserName());
            h.setModified_date(LocalDate.now());
            h.setDeleted(true);
            repository.save(h);
            delete = true;
        }catch (Exception e){
            e.getMessage();
        }
        return delete;
    }
    //************************************
    // Get all group cover plate
    //************************************
    @Override
    public List<GroupMaterialVO> getAllGroupCoverPlate() {
        List<GroupMaterialVO> list = new ArrayList<>();
        try {
            list = repository.getAllGroupsCoverPlate();
            if(list == null ){
                throw new Exception("Không tìm thấy ");
            }
        }catch (Exception e){
            e.getMessage();
        }
        return list;
    }
}
