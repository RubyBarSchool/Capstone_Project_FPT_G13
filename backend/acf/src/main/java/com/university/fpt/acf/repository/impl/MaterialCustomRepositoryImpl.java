package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.CheckMaterialForm;
import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.repository.EmployeeCustomRepository;
import com.university.fpt.acf.repository.MaterialCustomRepository;
import com.university.fpt.acf.vo.*;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Collection;
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
            sqlAcc.append(" select new com.university.fpt.acf.vo.SuggestMaterialVO(m.groupMaterial.name,m.id, m.name , " +
                    " m.company.name,m.percentChooseInMonth,img.name ) from Material m left join m.image img where m.deleted = false  ");
            sqlAcc.append(" ORDER by m.percentChooseInMonth desc ");
        }else if(materialSuggestFrom.getType().equals("quy")){
            sqlAcc.append(" select new com.university.fpt.acf.vo.SuggestMaterialVO(m.groupMaterial.name,m.id, m.name , " +
                    " m.company.name,m.percentChooseInMonth,img.name  ) from Material m left join m.image img where m.deleted = false  ");
            sqlAcc.append(" ORDER by m.percentChooseInQuarterOfYear desc ");
        }else if(materialSuggestFrom.getType().equals("nam")){
            sqlAcc.append(" select new com.university.fpt.acf.vo.SuggestMaterialVO(m.groupMaterial.name,m.id, m.name , " +
                    " m.company.name,m.percentChooseInMonth,img.name  ) from Material m left join m.image img where m.deleted = false  ");
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
        sql.append("select new com.university.fpt.acf.vo.MaterialVO( m.id,m.name,p.id,concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight),g.id,g.name,c.id,c.name,u.id,u.name,p.price,img.name ) from Material m left join m.image img inner join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=true and m.deleted = false and p.deleted = false ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null && !searchForm.getListIdCompany().isEmpty()){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()  ){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null && !searchForm.getListUnitId().isEmpty() ){
            sql.append(" and u.id in :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null && !searchForm.getListGroupID().isEmpty() ){
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
        sql.append("select COUNT(*) from Material m inner join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=true and m.deleted =false and p.deleted = false ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null && !searchForm.getListIdCompany().isEmpty()){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()  ){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null && !searchForm.getListUnitId().isEmpty() ){
            sql.append(" and u.id in :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null && !searchForm.getListGroupID().isEmpty() ){
            sql.append(" and g.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by m.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<MaterialInContactDetailVO> searchMaterialInAddProduct(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  new com.university.fpt.acf.vo.MaterialInContactDetailVO(pm.id,m.name," +
                "concat(fm.frameLength,'x',fm.frameWidth,'x',hm.frameHeight),gm.name,um.name,c.name" +
                ",pm.price) from Material m inner  join m.groupMaterial gm inner join m.company c " +
                "inner join m.priceMaterials pm inner join pm.unitMeasure um inner join  pm.heightMaterial hm " +
                "inner  join pm.frameMaterial fm where m.deleted = false and pm.deleted = false and gm.deleted = false and c.deleted = false and um.deleted = false and hm.deleted = false");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null && !searchForm.getListIdCompany().isEmpty()){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()  ){
            sql.append(" and concat(fm.frameLength,'x',fm.frameWidth,'x',hm.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null && !searchForm.getListUnitId().isEmpty() ){
            sql.append(" and um.id in :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null && !searchForm.getListGroupID().isEmpty() ){
            sql.append(" and gm.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by pm.id desc ");
        TypedQuery<MaterialInContactDetailVO> query = super.createQuery(sql.toString(), params, MaterialInContactDetailVO.class);
        return query.getResultList();
    }

    @Override
    public int totalSearchMaterialInAddProduct(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from Material m inner  join m.groupMaterial gm inner join m.company c " +
                "inner join m.priceMaterials pm inner join pm.unitMeasure um inner join  pm.heightMaterial hm " +
                "inner  join pm.frameMaterial fm where m.deleted = false and pm.deleted = false and gm.deleted = false and c.deleted = false and um.deleted = false and hm.deleted = false ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null && !searchForm.getListIdCompany().isEmpty()){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()  ){
            sql.append(" and concat(fm.frameLength,'x',fm.frameWidth,'x',hm.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null && !searchForm.getListUnitId().isEmpty() ){
            sql.append(" and um.id in :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null && !searchForm.getListGroupID().isEmpty() ){
            sql.append(" and gm.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by pm.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public List<MaterialVO> searchCoverSheet(SearchMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.MaterialVO( m.id,m.name,p.id,concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight),g.id,g.name,c.id,c.name,u.id,u.name,p.price,img.name ) from Material m left join m.image img inner join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=false and m.deleted =false and p.deleted = false ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null && !searchForm.getListIdCompany().isEmpty()){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()  ){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null && !searchForm.getListUnitId().isEmpty() ){
            sql.append(" and u.id in :listUnitId ");
            params.put("listUnitId", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null && !searchForm.getListGroupID().isEmpty() ){
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
        sql.append("select COUNT(*) from Material m inner join m.priceMaterials p inner join m.groupMaterial g inner join " +
                "m.company c inner join p.heightMaterial h inner join p.unitMeasure u inner join p.frameMaterial f where m.checkMaterial=false and m.deleted =false and p.deleted = false  ");
        if(searchForm.getCodeMaterial()!=null && !searchForm.getCodeMaterial().isEmpty()){
            sql.append(" and LOWER(m.name) like :code ");
            params.put("code", "%"+searchForm.getCodeMaterial().toLowerCase()+"%");
        }
        if (searchForm.getListIdCompany()!=null && !searchForm.getListIdCompany().isEmpty()){
            sql.append(" and c.id in :listID ");
            params.put("listID", searchForm.getListIdCompany());
        }
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()  ){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth,'x',h.frameHeight) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        if (searchForm.getListUnitId()!=null && !searchForm.getListUnitId().isEmpty() ){
            sql.append(" and u.id in :idUnit ");
            params.put("idUnit", searchForm.getListUnitId());
        }
        if (searchForm.getListGroupID()!=null && !searchForm.getListGroupID().isEmpty() ){
            sql.append(" and g.id in :listGroupId ");
            params.put("listGroupId", searchForm.getListGroupID());
        }
        sql.append(" ORDER by m.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public String getNameMaterial(CheckMaterialForm checkMaterialForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT distinct m.name FROM Material m where m.company.id =:idCompany and m.groupMaterial.id=:idGroup and m.name=:name and m.deleted=false and m.checkMaterial = true  ");
        params.put("name", checkMaterialForm.getName());
        params.put("idCompany", checkMaterialForm.getIdCompany());
        params.put("idGroup", checkMaterialForm.getIdGroup());
        TypedQuery<String> query = super.createQuery(sql.toString(), params, String.class);
        List<String> list = query.getResultList();
        return (list.size()==0)?null:list.get(0);
    }

    @Override
    public String getNameCoverSheet(CheckMaterialForm checkMaterialForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("SELECT distinct m.name FROM Material m where m.company.id =:idCompany and m.groupMaterial.id=:idGroup and m.name=:name and m.deleted=false and m.checkMaterial = false  ");
        params.put("idCompany", checkMaterialForm.getIdCompany());
        params.put("idGroup", checkMaterialForm.getIdGroup());
        params.put("name", checkMaterialForm.getName());
        TypedQuery<String> query = super.createQuery(sql.toString(), params, String.class);
        List<String> list = query.getResultList();
        return (list.size()==0)?null:list.get(0);
    }




}
