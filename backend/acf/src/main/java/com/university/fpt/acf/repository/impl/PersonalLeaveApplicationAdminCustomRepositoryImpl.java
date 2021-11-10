package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.SearchPersonalLeaveAdminApplicationForm;
import com.university.fpt.acf.repository.PersonalLeaveApplicationAdminCustomRepository;
import com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PersonalLeaveApplicationAdminCustomRepositoryImpl extends CommonRepository implements PersonalLeaveApplicationAdminCustomRepository {
    @Override
    public List<SearchPersonalLeaveApplicationAdminVO> searchApplication(SearchPersonalLeaveAdminApplicationForm searchApplication) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT new com.university.fpt.acf.vo.SearchPersonalLeaveApplicationAdminVO(p.id,p.created_date,p.dateStart,p.dateEnd,p.fileAttach,p.title,p.comment,p.content,e.id,e.fullName,p.accept,p.dateAccept) From PersonaLeaveApplication p left join p.employee e where p.deleted = false ");
        if(searchApplication.getNameEmployee() != null && !searchApplication.getNameEmployee().isEmpty()){
            sql.append(" and LOWER(e.fullName) like :name ");
            params.put("name","%"+searchApplication.getNameEmployee().toLowerCase()+"%");
        }
        if(searchApplication.getTitle() != null && !searchApplication.getTitle().isEmpty()){
            sql.append(" and LOWER(p.title) like :title ");
            params.put("title","%"+searchApplication.getTitle().toLowerCase()+"%");
        }
        if(searchApplication.getStatus() != null && !searchApplication.getStatus().isEmpty()){
            sql.append(" and p.accept=:status ");
            params.put("status",searchApplication.getStatus());
        }
        if (searchApplication.getDate() != null && !searchApplication.getDate().isEmpty()) {
            sql.append(" and  p.created_date BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchApplication.getDate().get(0));
            params.put("dateEnd", searchApplication.getDate().get(1));
        }
        sql.append(" ORDER by p.id desc ");
        TypedQuery<SearchPersonalLeaveApplicationAdminVO> query = super.createQuery(sql.toString(), params, SearchPersonalLeaveApplicationAdminVO.class);
        query.setFirstResult((searchApplication.getPageIndex() - 1)*searchApplication.getPageSize());
        query.setMaxResults(searchApplication.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchApplication(SearchPersonalLeaveAdminApplicationForm searchApplication) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append(" SELECT COUNT(*) From PersonaLeaveApplication p left join p.employee e where p.deleted = false ");
        if(searchApplication.getNameEmployee() != null && !searchApplication.getNameEmployee().isEmpty()){
            sql.append(" and LOWER(e.fullName) like :name ");
            params.put("name","%"+searchApplication.getNameEmployee().toLowerCase()+"%");
        }
        if(searchApplication.getTitle() != null && !searchApplication.getTitle().isEmpty()){
            sql.append(" and LOWER(p.title) like :title ");
            params.put("title","%"+searchApplication.getTitle().toLowerCase()+"%");
        }
        if(searchApplication.getStatus() != null && !searchApplication.getStatus().isEmpty()){
            sql.append(" and p.accept=:status ");
            params.put("status",searchApplication.getStatus());
        }
        if (searchApplication.getDate() != null && !searchApplication.getDate().isEmpty()) {
            sql.append(" and  p.created_date BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchApplication.getDate().get(0));
            params.put("dateEnd", searchApplication.getDate().get(1));
        }
        sql.append(" ORDER by p.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
}
