package com.university.fpt.acf.config.scheduled.service.impl;

import com.university.fpt.acf.config.scheduled.service.MaterialSuggestService;
import com.university.fpt.acf.entity.Material;
import com.university.fpt.acf.repository.ContactRepository;
import com.university.fpt.acf.repository.MaterialRepository;
import com.university.fpt.acf.vo.MaterialSuggestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialSuggestServiceImpl implements MaterialSuggestService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ContactRepository contactRepository;
    @Override
    public void calculatorMaterialInMonth() {
        //************************************
        //Calculating the amount of material usage by month
        // 1. Get the start and end date of the month to calculate
        // 2. Get each material and divide by the total number of materials
        //************************************
        try{
            LocalDate localDate = LocalDate.now();
            LocalDate dateLast = localDate.minusDays(1);
            LocalDate dateStart = LocalDate.of(dateLast.getYear(), dateLast.getMonthValue(),1);
            List<MaterialSuggestVO> materialSuggestVOList = materialRepository.getMaterialSuggest(dateStart,dateLast);
            Long totalProduct = contactRepository.getTotalProduct(dateStart,dateLast);
            List<Long> idMaterials = new ArrayList<>();
            for(MaterialSuggestVO materialSuggestVO : materialSuggestVOList){
                idMaterials.add(materialSuggestVO.getIdMaterial());
            }
            List<Material> materials = materialRepository.getMaterialByIds(idMaterials);
            for(int i = 0 ; i < materialSuggestVOList.size() ; i++){
                materials.get(i).setModified_by("JOB_AUTO");
                materials.get(i).setModified_date(LocalDate.now());
                materials.get(i).setPercentChooseInMonth(materialSuggestVOList.get(i).getCount().intValue()*1.0/totalProduct.intValue());
            }
            materialRepository.saveAll(materials);
        }catch (Exception exception){
            throw new RuntimeException("Không tinh đưuọc giá trị phần trăm vật liệu theo tháng");
        }
    }

    @Override
    public void calculatorMaterialInQuarterOfYear() {
        //************************************
        //Calculating the amount of material usage by quarter
        // 1. Get the start and end date of the month to calculate
        // 2. Get each material and divide by the total number of materials
        //************************************
        try{
            LocalDate localDate = LocalDate.now();
            LocalDate dateLast = localDate.minusDays(1);
            LocalDate monthsStart = dateLast.minusMonths(2);
            LocalDate dateStart = LocalDate.of(monthsStart.getYear(),monthsStart.getMonthValue(),1);
            List<MaterialSuggestVO> materialSuggestVOList = materialRepository.getMaterialSuggest(dateStart,dateLast);
            Long totalProduct = contactRepository.getTotalProduct(dateStart,dateLast);
            List<Long> idMaterials = new ArrayList<>();
            for(MaterialSuggestVO materialSuggestVO : materialSuggestVOList){
                idMaterials.add(materialSuggestVO.getIdMaterial());
            }
            List<Material> materials = materialRepository.getMaterialByIds(idMaterials);
            for(int i = 0 ; i < materialSuggestVOList.size() ; i++){
                materials.get(i).setModified_by("JOB_AUTO");
                materials.get(i).setModified_date(LocalDate.now());
                materials.get(i).setPercentChooseInQuarterOfYear(materialSuggestVOList.get(i).getCount().intValue()*1.0/totalProduct.intValue());
            }
            materialRepository.saveAll(materials);
        }catch (Exception exception){
            throw new RuntimeException("Không tinh đưuọc giá trị phần trăm vật liệu theo quý");
        }
    }

    @Override
    public void calculatorMaterialInYear() {
        //************************************
        //Calculating the amount of material usage by year
        // 1. Get the start and end date of the month to calculate
        // 2. Get each material and divide by the total number of materials
        //************************************
        try{
            LocalDate localDate = LocalDate.now();
            LocalDate dateLast = localDate.minusDays(1);
            LocalDate dateStart = LocalDate.of(dateLast.getYear(), 1,1);
            List<MaterialSuggestVO> materialSuggestVOList = materialRepository.getMaterialSuggest(dateStart,dateLast);
            Long totalProduct = contactRepository.getTotalProduct(dateStart,dateLast);
            List<Long> idMaterials = new ArrayList<>();
            for(MaterialSuggestVO materialSuggestVO : materialSuggestVOList){
                idMaterials.add(materialSuggestVO.getIdMaterial());
            }
            List<Material> materials = materialRepository.getMaterialByIds(idMaterials);
            for(int i = 0 ; i < materialSuggestVOList.size() ; i++){
                materials.get(i).setModified_by("JOB_AUTO");
                materials.get(i).setModified_date(LocalDate.now());
                materials.get(i).setPercentChooseInYear(materialSuggestVOList.get(i).getCount().intValue()*1.0/totalProduct.intValue());
            }
            materialRepository.saveAll(materials);
        }catch (Exception ex){
            throw new RuntimeException("Không tinh đưuọc giá trị phần trăm vật liệu theo năm");
        }
    }
}
