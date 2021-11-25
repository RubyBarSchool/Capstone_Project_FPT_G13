package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.repository.PunishCustomRepository;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PunishCustomRepositoryImpl extends CommonRepository implements PunishCustomRepository {
    @Override
    public List<SearchBonusAdminVO> searchPunish(SearchBonusAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status," +
                "b.effectiveDate,e.id,e.fullName) from BonusPenalty  b  inner  join  b.employees e where b.deleted=false and b.bonus = false  ");

        if(searchForm.getTitle()!=null && !searchForm.getTitle().isEmpty()){
            sql.append(" and LOWER(b.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if(searchForm.getStatus()!=null){
            sql.append(" and b.status =:status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by b.id desc ");
        TypedQuery<SearchBonusAdminVO> query = super.createQuery(sql.toString(),params, SearchBonusAdminVO.class);
        query.setFirstResult((searchForm.getPageIndex()-1)* searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchPunish(SearchBonusAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from BonusPenalty b where b.deleted=false and b.bonus = false");

        if(searchForm.getTitle()!=null && !searchForm.getTitle().isEmpty()){
            sql.append(" and LOWER(b.title) like :title ");
            params.put("title","%"+searchForm.getTitle().toLowerCase()+"%");
        }
        if(searchForm.getStatus()!=null ){
            sql.append(" and b.status =:status ");
            params.put("status",searchForm.getStatus());
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by b.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(),params, Long.class);
        return query.getSingleResult().intValue();
    }
    @Override
    public List<SearchBonusAdminVO> searchPunishUser(String username, BonusPunishForm bonusPunishForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();

        sql.append("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status," +
                "b.effectiveDate,e.id,e.fullName) from Account a inner join a.employee e inner join e.bonusPenalties b  where b.deleted" +
                " = false and b.status = true and b.bonus = false and a.username = :username");
        params.put("username", username);
        if(bonusPunishForm.getCheckNow()){
            LocalDate localDate = LocalDate.now();
            int day = localDate.getDayOfMonth();
            LocalDate locaDateStart = LocalDate.now();
            LocalDate locaDateEnd = LocalDate.now();
            if (day < 10) {
                LocalDate localDate1 = localDate.minusMonths(1);
                locaDateStart = LocalDate.of(localDate1.getYear(), localDate1.getMonth(), 10);
                locaDateEnd = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
            } else {
                LocalDate localDate2 = localDate.plusMonths(1);
                locaDateStart = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                locaDateEnd = LocalDate.of(localDate2.getYear(), localDate2.getMonth(), 10);
            }

            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", locaDateStart);
            params.put("dateEnd", locaDateEnd);
        }else{
            if(bonusPunishForm.getDate()!=null){
                LocalDate localDate = bonusPunishForm.getDate();
                int day = localDate.getDayOfMonth();
                LocalDate locaDateStart = LocalDate.now();
                LocalDate locaDateEnd = LocalDate.now();
                if (day < 10) {
                    LocalDate localDate1 = localDate.minusMonths(1);
                    locaDateStart = LocalDate.of(localDate1.getYear(), localDate1.getMonth(), 10);
                    locaDateEnd = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                } else {
                    LocalDate localDate2 = localDate.plusMonths(1);
                    locaDateStart = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                    locaDateEnd = LocalDate.of(localDate2.getYear(), localDate2.getMonth(), 10);
                }

                sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
                params.put("dateStart", locaDateStart);
                params.put("dateEnd", locaDateEnd);
            }else{
                LocalDate localDate = LocalDate.now();
                int day = localDate.getDayOfMonth();
                LocalDate locaDateEnd = LocalDate.now();
                if (day < 10) {
                    LocalDate localDate1 = localDate.minusMonths(1);
                    locaDateEnd = LocalDate.of(localDate1.getYear(), localDate1.getMonth(), 10);
                } else {
                    locaDateEnd = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                }
                sql.append(" and  b.effectiveDate < :dateEnd ");
                params.put("dateEnd", locaDateEnd);
            }
        }
        sql.append(" ORDER by b.effectiveDate desc ");
        TypedQuery<SearchBonusAdminVO> query = super.createQuery(sql.toString(),params, SearchBonusAdminVO.class);
        query.setFirstResult((bonusPunishForm.getPageIndex()-1)* bonusPunishForm.getPageSize());
        query.setMaxResults(bonusPunishForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchPunishUser(String username, BonusPunishForm bonusPunishForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from Account a inner join a.employee e inner join e.bonusPenalties b  where b.deleted" +
                " = false and b.status = true and b.bonus = false and a.username = :username");
        params.put("username", username);
        if(bonusPunishForm.getCheckNow()){
            LocalDate localDate = LocalDate.now();
            int day = localDate.getDayOfMonth();
            LocalDate locaDateStart = LocalDate.now();
            LocalDate locaDateEnd = LocalDate.now();
            if (day < 10) {
                LocalDate localDate1 = localDate.minusMonths(1);
                locaDateStart = LocalDate.of(localDate1.getYear(), localDate1.getMonth(), 10);
                locaDateEnd = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
            } else {
                LocalDate localDate2 = localDate.plusMonths(1);
                locaDateStart = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                locaDateEnd = LocalDate.of(localDate2.getYear(), localDate2.getMonth(), 10);
            }

            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", locaDateStart);
            params.put("dateEnd", locaDateEnd);
        }else{
            if(bonusPunishForm.getDate()!=null){
                LocalDate localDate = bonusPunishForm.getDate();
                int day = localDate.getDayOfMonth();
                LocalDate locaDateStart = LocalDate.now();
                LocalDate locaDateEnd = LocalDate.now();
                if (day < 10) {
                    LocalDate localDate1 = localDate.minusMonths(1);
                    locaDateStart = LocalDate.of(localDate1.getYear(), localDate1.getMonth(), 10);
                    locaDateEnd = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                } else {
                    LocalDate localDate2 = localDate.plusMonths(1);
                    locaDateStart = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                    locaDateEnd = LocalDate.of(localDate2.getYear(), localDate2.getMonth(), 10);
                }

                sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
                params.put("dateStart", locaDateStart);
                params.put("dateEnd", locaDateEnd);
            }else{
                LocalDate localDate = LocalDate.now();
                int day = localDate.getDayOfMonth();
                LocalDate locaDateEnd = LocalDate.now();
                if (day < 10) {
                    locaDateEnd = LocalDate.of(localDate.getYear(), localDate.getMonth(), 10);
                } else {
                    LocalDate localDate2 = localDate.plusMonths(1);
                    locaDateEnd = LocalDate.of(localDate2.getYear(), localDate2.getMonth(), 10);
                }
                sql.append(" and  b.effectiveDate <= :dateEnd ");
                params.put("dateEnd", locaDateEnd);
            }
        }
        sql.append("  ORDER by b.effectiveDate desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);
        return query.getSingleResult().intValue();
    }
}
