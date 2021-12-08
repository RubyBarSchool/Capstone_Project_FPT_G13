package com.university.fpt.acf.material;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.university.fpt.acf.form.AddMaterialForm;
import com.university.fpt.acf.form.MaterialSuggestFrom;
import com.university.fpt.acf.form.SearchMaterialForm;
import com.university.fpt.acf.form.UpdateMaterialForm;
import com.university.fpt.acf.service.impl.MaterialServiceImpl;
import com.university.fpt.acf.vo.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class MaterialServiceTest {
    @Autowired
    private MaterialServiceImpl underTest;

    @Test
    void searchSuggestMaterial() {
        //given
        MaterialSuggestFrom materialSuggestFrom = new MaterialSuggestFrom();
        materialSuggestFrom.setCount(10);
        materialSuggestFrom.setType("thang");
        //when
        List<SuggestMaterialVO> expectedList = underTest.searchSuggestMaterial(materialSuggestFrom);
        //then
        assertThat(expectedList.size()==10).isTrue();
    }

    @Test
    void searchMaterial() {
        //given
        SearchMaterialForm searchMaterialForm = new SearchMaterialForm();
        searchMaterialForm.setCodeMaterial("");
        searchMaterialForm.setFrame("");
        List<Long> listGroup = new ArrayList<>();
        searchMaterialForm.setListGroupID(listGroup);
        List<Long> listCompany = new ArrayList<>();
        searchMaterialForm.setListIdCompany(listCompany);
        searchMaterialForm.setPageIndex(1);
        searchMaterialForm.setPageSize(10);
        //when
        List<MaterialVO> listExpected = underTest.searchMaterial(searchMaterialForm);
        //then
        assertThat(listExpected.size()==10).isTrue();
    }

    @Test
    void totalSearchMaterial() {
        //given
        SearchMaterialForm searchMaterialForm = new SearchMaterialForm();
        searchMaterialForm.setCodeMaterial("");
        searchMaterialForm.setFrame("");
        List<Long> listGroup = new ArrayList<>();
        searchMaterialForm.setListGroupID(listGroup);
        List<Long> listCompany = new ArrayList<>();
        searchMaterialForm.setListIdCompany(listCompany);
        searchMaterialForm.setPageIndex(1);
        searchMaterialForm.setPageSize(10);
        //when
        Integer expected = underTest.totalSearchMaterial(searchMaterialForm);
        //then
        assertThat(expected==10).isTrue();
    }

    @Test
    void searchMaterialInAddProduct() {
        //given
        SearchMaterialForm searchMaterialForm = new SearchMaterialForm();
        searchMaterialForm.setCodeMaterial("");
        searchMaterialForm.setFrame("");
        List<Long> listGroup = new ArrayList<>();
        searchMaterialForm.setListGroupID(listGroup);
        List<Long> listCompany = new ArrayList<>();
        searchMaterialForm.setListIdCompany(listCompany);
        searchMaterialForm.setPageIndex(1);
        searchMaterialForm.setPageSize(10);
        //when
        List<MaterialInContactDetailVO> expectedList = underTest.searchMaterialInAddProduct(searchMaterialForm);
        //then
        assertThat(expectedList.size()==10).isTrue();
    }

    @Test
    void totalSearchMaterialInAddProduct() {
        //given
        SearchMaterialForm searchMaterialForm = new SearchMaterialForm();
        searchMaterialForm.setCodeMaterial("");
        searchMaterialForm.setFrame("");
        List<Long> listGroup = new ArrayList<>();
        searchMaterialForm.setListGroupID(listGroup);
        List<Long> listCompany = new ArrayList<>();
        searchMaterialForm.setListIdCompany(listCompany);
        searchMaterialForm.setPageIndex(1);
        searchMaterialForm.setPageSize(10);
        //when
        Integer expected = underTest.totalSearchMaterialInAddProduct(searchMaterialForm);
        //then
        assertThat(expected==10).isTrue();
    }

    @Test
    void addMaterial() {
        //given
        AddMaterialForm addMaterialForm = new AddMaterialForm();
        List<String> listName = new ArrayList<>();
        listName.add("Tấm ván HDE");
        addMaterialForm.setListName(listName);
        addMaterialForm.setIdCompany(185l);
        addMaterialForm.setIdUnit(1l);
        addMaterialForm.setIdGroup(7l);
        List<Long> listHeight = new ArrayList<>();
        listHeight.add(1l);
        addMaterialForm.setListIdHeight(listHeight);
        List<Long> listFrame = new ArrayList<>();
        listFrame.add(197l);
        addMaterialForm.setListIdFrame(listFrame);
        addMaterialForm.setPrice("550000");
        addMaterialForm.setImage("");
        //when
        Boolean expected = underTest.addMaterial(addMaterialForm);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void deleteMaterial() {

    }

    @Test
    void updateMaterial() {
        //given
        UpdateMaterialForm updateMaterialForm = new UpdateMaterialForm();
        updateMaterialForm.setIdParameter(341l);
        updateMaterialForm.setPrice("600000");
        updateMaterialForm.setImage("");
        //when
        Boolean expected = underTest.updateMaterial(updateMaterialForm);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void addUnitInMaterial() {
    }

    @Test
    void addFrameHeightMaterial() {
    }

    @Test
    @Disabled
    void getAllMaterial() {
        //when
        List<GetAllMaterialVO> expectedList = underTest.getAllMaterial();
        //then
        assertThat(expectedList.size()==12).isTrue();

    }

    @Test
    void getMaterialByUnit() {
        //given
        Long idUnit =1l;
        //when
        List<GetAllMaterialVO> expectedList = underTest.getMaterialByUnit(idUnit);
        System.out.println(expectedList.size());
        //then
        assertThat(expectedList.size()==4).isTrue();
    }

    @Test
    void getHeightSByMaterialAndFrame() {
        //given
        Long idMaterial =8l;
        Long idFrame = 5l;
        //when
        List<HeightMaterialVO> expectedList = underTest.getHeightSByMaterialAndFrame(idMaterial,idFrame);
        //then

    }

    @Test
    void getMaterialByHeightFrame() {
        //given
        Long idHeight =1l;
        Long idFrame= 5l;
        //when
        List<GetAllMaterialVO> expectedList = underTest.getMaterialByHeightFrame(idHeight,idFrame);
        //then

    }

    @Test
    void getFrameByMaterialAndHeight() {
        //given
        Long idMaterial=8l;
        Long idHeight=1l;
        //when
        List<FrameMaterialVO> expectedList = underTest.getFrameByMaterialAndHeight(idMaterial,idHeight);
        //then
    }
}