package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchColorForm;
import com.university.fpt.acf.repository.ColorCustomRepository;
import com.university.fpt.acf.vo.ColorVO;
import com.university.fpt.acf.vo.PositionResponseVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ColorCustomRepositoryImpl extends CommonRepository implements ColorCustomRepository {
    @Override
    public List<ColorVO> searchColor(SearchColorForm searchColorForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.ColorVO(c.id,c.code,c.name,c.image,cp.id,cp.name) " +
                "FROM Color c inner join c.company cp where c.deleted = false ");
        if(!searchColorForm.getName().isEmpty() && searchColorForm.getName()!=null){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+searchColorForm.getName().toLowerCase()+"%");
        }
        if(!searchColorForm.getCode().isEmpty() && searchColorForm.getCode()!=null){
            sql.append(" and LOWER(c.code) like :code ");
            params.put("code","%"+searchColorForm.getCode().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<ColorVO> query = super.createQuery(sql.toString(),params, ColorVO.class);
        query.setFirstResult((searchColorForm.getPageIndex()-1)* searchColorForm.getPageSize());
        query.setMaxResults(searchColorForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int getTotalSearchColor(SearchColorForm searchColorForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) " +
                "FROM Color c inner join c.company cp where c.deleted = false ");
        if(!searchColorForm.getName().isEmpty() && searchColorForm.getName()!=null){
            sql.append(" and LOWER(c.name) like :name ");
            params.put("name","%"+searchColorForm.getName().toLowerCase()+"%");
        }
        if(!searchColorForm.getCode().isEmpty() && searchColorForm.getCode()!=null){
            sql.append(" and LOWER(c.code) like :code ");
            params.put("code","%"+searchColorForm.getCode().toLowerCase()+"%");
        }
        sql.append(" ORDER by c.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
