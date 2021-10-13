package com.university.fpt.acf.common.repository;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Map;

@Component
public class CommonRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManager getEntityManager(){
        return  this.entityManager;
    }

    public void initQueryParams(Query query, Map<String,Object> params){
        if(!params.isEmpty()){
            for(Map.Entry<String,Object> entry : params.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    public <T>TypedQuery<T> createQuery(String sql, Map<String,Object> params,Class<T> tClass){
        TypedQuery<T> query = this.getEntityManager().createQuery(sql,tClass);
        this.initQueryParams(query,params);
        return query;
    }

    public Query createNativeQuery(String sql, Map<String, Object> params){
        Query query = entityManager.createNativeQuery(sql);
        this.initQueryParams(query,params);
        return query;
    }
}
