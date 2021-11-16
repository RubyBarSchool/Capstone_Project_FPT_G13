package com.university.fpt.acf.repository.impl;

import com.university.fpt.acf.common.repository.CommonRepository;
import com.university.fpt.acf.entity.PriceMaterial;
import com.university.fpt.acf.repository.PriceMaterialCustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.Map;

@Repository
public class PriceMaterialCustomRepositoryImpl extends CommonRepository implements PriceMaterialCustomRepository {
}
