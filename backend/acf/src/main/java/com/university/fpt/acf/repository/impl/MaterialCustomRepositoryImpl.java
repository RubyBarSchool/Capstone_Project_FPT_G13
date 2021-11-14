package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.CheckMaterialForm;
import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.repository.MaterialCustomRepository;
import com.university.fpt.acf.vo.AttendanceVO;
import com.university.fpt.acf.vo.MaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;
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

    @Override
    public List<MaterialVO> searchMaterial(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.MaterialVO( m.id,m.name,p.id,concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight),g.id,g.name,c.id,c.name,u.id,u.name,p.price ) from Material m left join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=true  ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if(!searchForm.getFrame().isEmpty() && searchForm.getFrame()==null){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null ){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if (searchForm.getListUnitId()!=null ){
            sql.append(" and u.id in :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null ){
            sql.append(" and g.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by m.id desc ");
        TypedQuery<MaterialVO> query = super.createQuery(sql.toString(), params, MaterialVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchMaterial(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from Material m left join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=true  ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null ){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(!searchForm.getFrame().isEmpty() && searchForm.getFrame()==null){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null ){
            sql.append(" and u.id= :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null ){
            sql.append(" and g.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by m.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<MaterialVO> searchCoverSheet(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.MaterialVO( m.id,m.name,p.id,concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight),g.id,g.name,c.id,c.name,u.id,u.name,p.price ) from Material m left join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=false  ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null ){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(!searchForm.getFrame().isEmpty() && searchForm.getFrame()==null){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null ){
            sql.append(" and u.id= :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null ){
            sql.append(" and g.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by m.id desc ");
        TypedQuery<MaterialVO> query = super.createQuery(sql.toString(), params, MaterialVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchCoverSheet(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from Material m left join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=false  ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null ){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(!searchForm.getFrame().isEmpty() && searchForm.getFrame()==null){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null ){
            sql.append(" and u.id= :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null ){
            sql.append(" and g.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by m.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public Long getIdMaterial(CheckMaterialForm checkMaterialForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select m.id from Material m where m.checkMaterial = true and m.name=:name and m.company.id=:idCompany and m.company.id=:idGroup ");
        params.put("name", "%"+checkMaterialForm.getName().toLowerCase()+"%");
        params.put("idCompany", checkMaterialForm.getIdCompany());
        params.put("idGroup", checkMaterialForm.getIdGroup());
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult();
    }
}
