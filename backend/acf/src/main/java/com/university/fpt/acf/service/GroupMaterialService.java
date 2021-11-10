package com.university.fpt.acf.service;

import com.university.fpt.acf.vo.GroupMaterialVO;
import com.university.fpt.acf.vo.HeightMaterialVO;

import java.util.List;

public interface GroupMaterialService {
    Boolean addGroupMaterial(String name);
    Boolean deleteGroupMaterial(Long id);
    List<GroupMaterialVO> getAllGroupMaterial();
    Boolean addGroupCoverPlate(String name);
    Boolean deleteGroupCoverPlate(Long id);
    List<GroupMaterialVO> getAllGroupCoverPlate();
}
