package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.repository.MaterialCustomRepository;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.MaterialVO;
import com.university.fpt.acf.vo.SuggestMaterialVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialCustomRepository materialCustomRepository;
    @Override
    public List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom) {
        List<SuggestMaterialVO> suggestMaterialVOS = new ArrayList<>();
        try{
            suggestMaterialVOS = materialCustomRepository.searchSuggestMaterial(materialSuggestFrom);
        }catch (Exception e){
            throw new RuntimeException("Không lấy được gợi ý tấm phủ");
        }
        return suggestMaterialVOS;
    }

    @Override
    public List<MaterialVO> searchMaterial(SearchMaterialForm searchForm) {
        List<MaterialVO> list= new ArrayList<>();
        try{
            list = materialCustomRepository.searchMaterial(searchForm);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public int totalSearchMaterial(SearchMaterialForm searchForm) {
        int size=0;
        try{
            size = materialCustomRepository.totalSearchMaterial(searchForm);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return size;
    }

    @Override
    public List<MaterialVO> searchCoverSheet(SearchMaterialForm searchForm) {
        List<MaterialVO> list= new ArrayList<>();
        try{
            list = materialCustomRepository.searchMaterial(searchForm);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public int totalSearchCoverSheet(SearchMaterialForm searchForm) {
        int size=0;
        try{
            size = materialCustomRepository.totalSearchCoverSheet(searchForm);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return size;
    }
}
