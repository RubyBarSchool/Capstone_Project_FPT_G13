package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchAllFrame;
import com.university.fpt.acf.form.SearchFrameMaterialForm;
import com.university.fpt.acf.form.SearchHeightMaterialForm;
import com.university.fpt.acf.repository.FrameMaterialCustomRepository;
import com.university.fpt.acf.vo.CompanyVO;
import com.university.fpt.acf.vo.FrameMaterialVO;
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
    public List<SearchFrameMaterialVO> searchFrame(SearchFrameMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.SearchFrameMaterialVO(f.id,f.frameLength,f.frameWidth) from FrameMaterial f where f.deleted = false  ");
        if(searchForm.getLength()!=null && !searchForm.getLength().isEmpty()){
            sql.append(" and LOWER(f.frameLength) like :length ");
            params.put("length", "%"+searchForm.getLength().toLowerCase()+"%");
        }
        if (searchForm.getWidth()!=null && !searchForm.getWidth().isEmpty()){
            sql.append(" and LOWER(f.frameWidth) like :width ");
            params.put("width", "%"+searchForm.getWidth().toLowerCase()+"%");
        }
        sql.append(" ORDER by f.id desc ");
        TypedQuery<SearchFrameMaterialVO> query = super.createQuery(sql.toString(), params, SearchFrameMaterialVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public List<FrameMaterialVO> searchAllFrame(SearchAllFrame searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select new com.university.fpt.acf.vo.FrameMaterialVO(f.id,concat(f.frameLength,'x',f.frameWidth)) from FrameMaterial f where f.deleted = false  ");
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        sql.append(" ORDER by f.id desc ");
        TypedQuery<FrameMaterialVO> query = super.createQuery(sql.toString(), params, FrameMaterialVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchFrame(SearchFrameMaterialForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from FrameMaterial f  where f.deleted = false  ");
        if(searchForm.getLength()!=null && !searchForm.getLength().isEmpty()){
            sql.append(" and f.frameLength=:length ");
            params.put("length", searchForm.getLength().toLowerCase());
        }
        if (searchForm.getWidth()!=null && !searchForm.getWidth().isEmpty()){
            sql.append(" and f.frameWidth=:width ");
            params.put("width", searchForm.getWidth().toLowerCase());
        }
        sql.append(" ORDER by f.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }

    @Override
    public int totalsearchAllFrame(SearchAllFrame searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select COUNT(*) from FrameMaterial f where f.deleted = false  ");
        if(searchForm.getFrame()!=null && !searchForm.getFrame().isEmpty()){
            sql.append(" and concat(f.frameLength,'x',f.frameWidth) like :frame ");
            params.put("frame", "%"+searchForm.getFrame().toLowerCase()+"%");
        }
        sql.append(" ORDER by f.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }
}
