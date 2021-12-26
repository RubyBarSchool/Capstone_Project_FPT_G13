package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.form.BonusPunishForm;
import com.university.fpt.acf.form.SearchBonusAdminForm;
import com.university.fpt.acf.form.SearchBonusAndPunishForm;
import com.university.fpt.acf.repository.BonusCustomRepository;
import com.university.fpt.acf.vo.SearchBonusAdminVO;
import com.university.fpt.acf.vo.SearchBonusAndPunishVO;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BonusCustomRepositoryImpl extends CommonRepository implements BonusCustomRepository {
    @Override
    public List<SearchBonusAdminVO> searchBonus(SearchBonusAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status," +
                "b.effectiveDate,e.id,e.fullName) from BonusPenalty  b  left  join  b.employees e where b.deleted=false and b.bonus=true ");

        if (searchForm.getTitle() != null && !searchForm.getTitle().isEmpty()) {
            sql.append(" and LOWER(b.title) like :title ");
            params.put("title", "%" + searchForm.getTitle().toLowerCase() + "%");
        }
        if (searchForm.getStatus() != null) {
            sql.append(" and b.status = :status ");
            params.put("status", searchForm.getStatus());
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by b.id desc ");
        TypedQuery<SearchBonusAdminVO> query = super.createQuery(sql.toString(), params, SearchBonusAdminVO.class);
        query.setFirstResult((searchForm.getPageIndex() - 1) * searchForm.getPageSize());
        query.setMaxResults(searchForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchBonus(SearchBonusAdminForm searchForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from BonusPenalty b where b.deleted=false and b.bonus = true");

        if (searchForm.getTitle() != null && !searchForm.getTitle().isEmpty()) {
            sql.append(" and LOWER(b.title) like :title ");
            params.put("title", "%" + searchForm.getTitle().toLowerCase() + "%");
        }
        if (searchForm.getStatus() != null) {
            sql.append(" and b.status = :status ");
            params.put("status", searchForm.getStatus());
        }
        if (searchForm.getDate() != null && !searchForm.getDate().isEmpty()) {
            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchForm.getDate().get(0));
            params.put("dateEnd", searchForm.getDate().get(1));
        }
        sql.append(" ORDER by b.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);

        return query.getSingleResult().intValue();
    }

    @Override
    public List<SearchBonusAndPunishVO> searchBonusAndPunish(String userName,SearchBonusAndPunishForm searchBonusAndPunishForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  new com.university.fpt.acf.vo.SearchBonusAndPunishVO(b.id,b.title,b.reason,b.money,b.status,b.effectiveDate,b.bonus,b.created_by) from Account a inner  join  a.employee e inner  join  e.bonusPenalties b where b.deleted=false and b.status =true");
        sql.append(" and a.username =: username");
        params.put("username", userName);
        if (searchBonusAndPunishForm.getTitle() != null && !searchBonusAndPunishForm.getTitle().isEmpty()) {
            sql.append(" and LOWER(b.title) like :title ");
            params.put("title", "%" + searchBonusAndPunishForm.getTitle().toLowerCase() + "%");
        }
        if (searchBonusAndPunishForm.getStatus() != null) {
            sql.append(" and b.status = :status ");
            params.put("status", searchBonusAndPunishForm.getStatus());
        }
        if (searchBonusAndPunishForm.getBonus() != null) {
            sql.append(" and b.bonus = :bonus ");
            params.put("bonus", searchBonusAndPunishForm.getBonus());
        }
        if (searchBonusAndPunishForm.getDate() != null && !searchBonusAndPunishForm.getDate().isEmpty()) {
            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchBonusAndPunishForm.getDate().get(0));
            params.put("dateEnd", searchBonusAndPunishForm.getDate().get(1));
        }
        sql.append(" ORDER by b.id desc ");
        TypedQuery<SearchBonusAndPunishVO> query = super.createQuery(sql.toString(), params, SearchBonusAndPunishVO.class);
        query.setFirstResult((searchBonusAndPunishForm.getPageIndex() - 1) * searchBonusAndPunishForm.getPageSize());
        query.setMaxResults(searchBonusAndPunishForm.getPageSize());
        return query.getResultList();
    }

    @Override
    public int totalSearchBonusAndPunish(String userName,SearchBonusAndPunishForm searchBonusAndPunishForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from Account a inner  join  a.employee e inner  join  e.bonusPenalties b where b.deleted=false and b.status =true");
        sql.append(" and a.username =: username");
        params.put("username", userName);
        if (searchBonusAndPunishForm.getTitle() != null && !searchBonusAndPunishForm.getTitle().isEmpty()) {
            sql.append(" and LOWER(b.title) like :title ");
            params.put("title", "%" + searchBonusAndPunishForm.getTitle().toLowerCase() + "%");
        }
        if (searchBonusAndPunishForm.getStatus() != null) {
            sql.append(" and b.status = :status ");
            params.put("status", searchBonusAndPunishForm.getStatus());
        }
        if (searchBonusAndPunishForm.getBonus() != null) {
            sql.append(" and b.bonus = :bonus ");
            params.put("bonus", searchBonusAndPunishForm.getBonus());
        }
        if (searchBonusAndPunishForm.getDate() != null && !searchBonusAndPunishForm.getDate().isEmpty()) {
            sql.append(" and  b.effectiveDate BETWEEN :dateStart and :dateEnd ");
            params.put("dateStart", searchBonusAndPunishForm.getDate().get(0));
            params.put("dateEnd", searchBonusAndPunishForm.getDate().get(1));
        }
        sql.append(" ORDER by b.id desc ");
        TypedQuery<Long> query = super.createQuery(sql.toString(), params, Long.class);

        return query.getSingleResult().intValue();
    }

    @Override
    public List<SearchBonusAdminVO> searchBonusUser(String username, BonusPunishForm bonusPunishForm) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  new com.university.fpt.acf.vo.SearchBonusAdminVO(b.id,b.title,b.reason,b.money,b.status," +
                "b.effectiveDate,e.id,e.fullName) from Account a inner join a.employee e inner join e.bonusPenalties b  where b.deleted" +
                " = false and b.status = true and b.bonus = true and a.username = :username");
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
    public int totalSearchBonusUser(String username,BonusPunishForm bonusPunishForm) {

        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select  COUNT(*) from Account a inner join a.employee e inner join e.bonusPenalties b  where b.deleted" +
                " = false and b.status = true and b.bonus = true and a.username = :username");
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
