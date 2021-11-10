package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.repository.FrameMaterialCustomRepository;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.HeightMaterialVO;
import com.university.fpt.acf.vo.SearchFrameMaterialVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FrameMaterialCustomRepositoryImpl extends CommonRepository implements FrameMaterialCustomRepository {
    @Override
    public List<HeightMaterialVO> getAllHeightNotFrameTable(SearchHeightMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.HeightMaterialVO( h.id, h.height ) from HeightMaterial h ");
        if ((searchForm.getLength()!=null && !searchForm.getLength().isEmpty())||
                (searchForm.getWidth()!=null && !searchForm.getLength().isEmpty())||
                (searchForm.getIdUnit()!=null)){
            sql.append("where h.id NOT IN ( select f.heightMaterial.id from FrameMaterial f where  1=1 ");

            if(searchForm.getLength()!=null && !searchForm.getLength().isEmpty()){
                sql.append(" and f.frameLength=:length ");
                params.put("length", searchForm.getLength().toLowerCase());
            }
            if (searchForm.getWidth()!=null && !searchForm.getLength().isEmpty()){
                sql.append(" and f.frameWidth=:width ");
                params.put("width", searchForm.getWidth().toLowerCase());
            }
            if(searchForm.getIdUnit()!=null){
                sql.append(" and f.unitMeasure.id =:id ");
                params.put("id", searchForm.getIdUnit());
            }

            sql.append(" ) ");
        }
        sql.append(" ORDER by h.id desc ");
        TypedQuery<HeightMaterialVO> query = super.createQuery(sql.toString(), params, HeightMaterialVO.class);
        return query.getResultList();
    }

    @Override
    public List<SearchFrameMaterialVO> searchFrame(SearchFrameMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.SearchFrameMaterialVO(f.id,f.frameLength,f.frameWidth,u.name,h.height) from FrameMaterial f inner join f.unitMeasure u inner join f.heightMaterial h where 1=1  ");
        if(searchForm.getLength()!=null && !searchForm.getLength().isEmpty()){
            sql.append(" and LOWER(f.frameLength) like :length ");
            params.put("length", "%"+searchForm.getLength().toLowerCase()+"%");
        }
        if (searchForm.getWidth()!=null && !searchForm.getWidth().isEmpty()){
            sql.append(" and LOWER(f.frameWidth) like :width ");
            params.put("width", searchForm.getWidth().toLowerCase());
        }
        if(searchForm.getIdUnit()!=null){
            sql.append(" and f.unitMeasure.id =:id ");
            params.put("id", searchForm.getIdUnit());
        }
        sql.append(" ORDER by f.id desc ");
        TypedQuery<SearchFrameMaterialVO> query = super.createQuery(sql.toString(), params, SearchFrameMaterialVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchFrame(SearchFrameMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from FrameMaterial f inner join f.unitMeasure u inner join f.heightMaterial h where 1=1  ");
        if(searchForm.getLength()!=null && !searchForm.getLength().isEmpty()){
            sql.append(" and f.frameLength=:length ");
            params.put("length", searchForm.getLength().toLowerCase());
        }
        if (searchForm.getWidth()!=null && !searchForm.getWidth().isEmpty()){
            sql.append(" and f.frameWidth=:width ");
            params.put("width", searchForm.getWidth().toLowerCase());
        }
        if(searchForm.getIdUnit()!=null){
            sql.append(" and f.unitMeasure.id =:id ");
            params.put("id", searchForm.getIdUnit());
        }
        sql.append(" ORDER by f.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }
}
