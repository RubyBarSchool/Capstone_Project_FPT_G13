package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.repository.MaterialCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.SuggestMaterialVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MaterialCustomRepositoryImpl extends CommonRepository implements MaterialCustomRepository {
    @Override
    public List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom) {
        StringBuilder sqlAcc = new StringBuilder("");
        Map<String, Object> paramsAcc = new HashMap<>();
        if(materialSuggestFrom.getType().equals("thang")){
            sqlAcc.append(" select new com.university.fpt.acf.vo.SuggestMaterialVO(m.groupMaterial.name, m.name , " +
                    "CONCAT(pm.frameMaterial.frameLength,' x ',pm.frameMaterial.frameWidth,' x '," +
                    "pm.heightMaterial.frameHeight), pm.unitMeasure.name,m.company.name,m.percentChooseInMonth ) " +
                    " from Material m inner join m.priceMaterials pm   where m.deleted = false and m.checkMaterial = false ");
            sqlAcc.append(" ORDER by m.percentChooseInMonth desc ");
        }else if(materialSuggestFrom.getType().equals("quy")){
            sqlAcc.append(" select new com.university.fpt.acf.vo.SuggestMaterialVO(m.groupMaterial.name, m.name , " +
                    "CONCAT(pm.frameMaterial.frameLength,' x ',pm.frameMaterial.frameWidth,' x '," +
                    "pm.heightMaterial.frameHeight), pm.unitMeasure.name,m.company.name,m.percentChooseInQuarterOfYear ) " +
                    " from Material m inner join m.priceMaterials pm   where m.deleted = false and m.checkMaterial = false ");
            sqlAcc.append(" ORDER by m.percentChooseInQuarterOfYear desc ");
        }else if(materialSuggestFrom.getType().equals("name")){
            sqlAcc.append(" select new com.university.fpt.acf.vo.SuggestMaterialVO(m.groupMaterial.name, m.name , " +
                    "CONCAT(pm.frameMaterial.frameLength,' x ',pm.frameMaterial.frameWidth,' x '," +
                    "pm.heightMaterial.frameHeight), pm.unitMeasure.name,m.company.name,m.percentChooseInYear ) " +
                    " from Material m inner join m.priceMaterials pm   where m.deleted = false and m.checkMaterial = false ");
            sqlAcc.append(" ORDER by m.percentChooseInYear desc ");
        }
        TypedQuery<SuggestMaterialVO> queryAcc = super.createQuery(sqlAcc.toString(), paramsAcc, SuggestMaterialVO.class);
        queryAcc.setFirstResult(0);
        queryAcc.setMaxResults(materialSuggestFrom.getCount());
        List<SuggestMaterialVO> resultList = queryAcc.getResultList();
        return resultList;
    }
}
