package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.GetAllMaterialVO;
import com.university.fpt.acf.vo.MaterialVO;
import com.university.fpt.acf.vo.SuggestMaterialVO;
import com.university.fpt.acf.vo.UnitMeasureVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialCustomRepository materialCustomRepository;
    @Autowired
    private GroupMaterialRepository groupMaterialRepository;
    @Autowired
    private CompanyRespository companyRespository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private PriceMaterialCustomRepository priceCustomRepository;
    @Autowired
    private PriceMaterialRepository priceMaterialRepository;
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
            list = materialCustomRepository.searchCoverSheet(searchForm);
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

    @Override
    @Transactional
    public Boolean addMaterial(AddMaterialForm addForm) {
        Boolean check = false;
        try{
            List<String> list = addForm.getListName();
            for(int i=0 ;i<list.size();i++) {
                GroupMaterial groupMaterial = groupMaterialRepository.getGroupMaterialByID(addForm.getIdGroup());
                if(groupMaterial==null){
                    throw new RuntimeException("Không tồn tại nhóm vật liệu  ! ");
                }
                Company company =companyRespository.getCompanyById(addForm.getIdCompany());
                if(company==null){
                    throw new RuntimeException("Không tồn tại công ty vật liệu  ! ");
                }
                CheckMaterialForm a = new CheckMaterialForm();
                a.setName(list.get(i));
                a.setIdCompany(addForm.getIdCompany());
                a.setIdGroup(addForm.getIdGroup());
                Long idMaterial =materialCustomRepository.getIdCoverSheet(a);
                if(idMaterial==null){
                    Material m = new Material();
                    m.setName(list.get(i));
                    m.setGroupMaterial(groupMaterial);
                    m.setCompany(company);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    m.setModified_by(accountSercurity.getUserName());
                    m.setCreated_by(accountSercurity.getUserName());
                    m.setCreated_date(LocalDate.now());
                    List<PriceMaterial> listPriceMaterial = new ArrayList<>();
                    List<Long> listIdFrame = addForm.getListIdFrame();
                    for(int j=0;j<listIdFrame.size();j++){
                        List<Long> listIdHeight = addForm.getListIdHeight();
                        for(int k=0;k<listIdHeight.size();k++){
                            PriceMaterial pm = new PriceMaterial();
                            pm.setMaterial(m);
                            pm.setPrice(addForm.getPrice());
                            pm.setModified_by(accountSercurity.getUserName());
                            pm.setCreated_by(accountSercurity.getUserName());
                            pm.setCreated_date(LocalDate.now());
                            HeightMaterial h= new HeightMaterial();
                            h.setId(listIdHeight.get(k));
                            pm.setHeightMaterial(h);
                            UnitMeasure unit = new UnitMeasure();
                            unit.setId(addForm.getIdUnit());
                            pm.setUnitMeasure(unit);
                            FrameMaterial fm = new FrameMaterial();
                            fm.setId(listIdFrame.get(j));
                            pm.setFrameMaterial(fm);
                            listPriceMaterial.add(pm);
                        }
                    }
                    m.setPriceMaterials(listPriceMaterial);
                    materialRepository.saveAndFlush(m);
                    check=true;
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Không thêm mới được ! ");
        }
        return check;
    }

    @Override
    public Boolean deleteMaterial(Long id) {
        Boolean check = false;
        try{
            Material m = materialRepository.getMaterialById(id);
            m.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            m.setModified_by(accountSercurity.getUserName());
            m.setModified_date(LocalDate.now());
            materialRepository.save(m);
            check =true;

        }catch (Exception e){
            throw new RuntimeException("Không Xóa được ! ");
        }
        return check;
    }

    @Override
    public Boolean updateMaterial(UpdateMaterialForm updateForm) {
        Boolean check = false;
        try{
            PriceMaterialForm pmf = new PriceMaterialForm();
            pmf.setId(updateForm.getId());
            pmf.setIdFrame(updateForm.getIdFrame());
            pmf.setIdHeight(updateForm.getIdHeight());
            pmf.setIdUnit(updateForm.getIdUnit());

            PriceMaterial p = priceCustomRepository.getPriceMaterial(pmf);
            p.setPrice(updateForm.getPrice());

            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            priceMaterialRepository.save(p);
            check =true;

        }catch (Exception e){
            throw new RuntimeException("Không chỉnh sửa được ! ");
        }
        return check;
    }

    @Override
    public Boolean addCoverSheet(AddMaterialForm addForm) {
        Boolean check = false;
        try{
            List<String> list = addForm.getListName();
            for(int i=0 ;i<list.size();i++) {
                GroupMaterial groupCoverSheet = groupMaterialRepository.getGroupCoverPlateByID(addForm.getIdGroup());
                if(groupCoverSheet==null){
                    throw new RuntimeException("Không tồn tại nhóm tấm phủ  ! ");
                }
                Company company =companyRespository.getCompanyById(addForm.getIdCompany());
                if(company==null){
                    throw new RuntimeException("Không tồn tại công ty tấm phủ  ! ");
                }
                CheckMaterialForm a = new CheckMaterialForm();
                a.setName(list.get(i));
                a.setIdCompany(addForm.getIdCompany());
                a.setIdGroup(addForm.getIdGroup());
                Long idMaterial =materialCustomRepository.getIdCoverSheet(a);
                if(idMaterial==null){
                    Material m = new Material();
                    m.setCheckMaterial(false);
                    m.setName(list.get(i));
                    m.setGroupMaterial(groupCoverSheet);
                    m.setCompany(company);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    m.setModified_by(accountSercurity.getUserName());
                    m.setCreated_by(accountSercurity.getUserName());
                    m.setCreated_date(LocalDate.now());
                    List<PriceMaterial> listPriceMaterial = new ArrayList<>();
                    List<Long> listIdFrame = addForm.getListIdFrame();
                    for(int j=0;j<listIdFrame.size();j++){
                        List<Long> listIdHeight = addForm.getListIdHeight();
                        for(int k=0;k<listIdHeight.size();k++){
                            PriceMaterial pm = new PriceMaterial();
                            pm.setMaterial(m);
                            pm.setPrice(addForm.getPrice());
                            pm.setModified_by(accountSercurity.getUserName());
                            pm.setCreated_by(accountSercurity.getUserName());
                            pm.setCreated_date(LocalDate.now());
                            HeightMaterial h= new HeightMaterial();
                            h.setId(listIdHeight.get(k));
                            pm.setHeightMaterial(h);
                            UnitMeasure unit = new UnitMeasure();
                            unit.setId(addForm.getIdUnit());
                            pm.setUnitMeasure(unit);
                            FrameMaterial fm = new FrameMaterial();
                            fm.setId(listIdFrame.get(j));
                            pm.setFrameMaterial(fm);
                            listPriceMaterial.add(pm);
                        }
                    }
                    m.setPriceMaterials(listPriceMaterial);
                    materialRepository.saveAndFlush(m);
                    check=true;
                }
            }
        }catch (Exception e){
            throw new RuntimeException("Không thêm mới được ! ");
        }
        return check;
    }

    @Override
    public Boolean deleteCoverSheet(Long id) {
        Boolean check = false;
        try{
            Material m = materialRepository.getCoverSheetById(id);
            m.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            m.setModified_by(accountSercurity.getUserName());
            m.setModified_date(LocalDate.now());
            materialRepository.save(m);
            check =true;

        }catch (Exception e){
            throw new RuntimeException("Không Xóa được ! ");
        }
        return check;
    }

    @Override
    public Boolean updateCoverSheet(UpdateMaterialForm updateForm) {
        Boolean check = false;
        try{
            PriceMaterialForm pmf = new PriceMaterialForm();
            pmf.setId(updateForm.getId());
            pmf.setIdFrame(updateForm.getIdFrame());
            pmf.setIdHeight(updateForm.getIdHeight());
            pmf.setIdUnit(updateForm.getIdUnit());

            PriceMaterial p = priceCustomRepository.getPriceCoverSheet(pmf);
            p.setPrice(updateForm.getPrice());

            AccountSercurity accountSercurity = new AccountSercurity();
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            priceMaterialRepository.save(p);
            check =true;

        }catch (Exception e){
            throw new RuntimeException("Không chỉnh sửa được ! ");
        }
        return check;
    }

    @Override
    public Boolean addUnitInMaterial(AddUnitFrameHeightForm addForm) {
        Boolean check = false;
        try{
            PriceMaterial pm = new PriceMaterial();
            pm.setPrice(addForm.getPrice());
            HeightMaterial h= new HeightMaterial();
            h.setId(addForm.getIdHeight());
            pm.setHeightMaterial(h);
            UnitMeasure unit = new UnitMeasure();
            unit.setId(addForm.getIdUnit());
            pm.setUnitMeasure(unit);
            FrameMaterial fm = new FrameMaterial();
            fm.setId(addForm.getIdFrame());
            pm.setFrameMaterial(fm);
            AccountSercurity accountSercurity = new AccountSercurity();
            pm.setModified_by(accountSercurity.getUserName());
            pm.setCreated_by(accountSercurity.getUserName());
            pm.setModified_date(LocalDate.now());
            priceMaterialRepository.save(pm);
            check =true;
        }catch (Exception e){
            throw new RuntimeException("Không thêm được ! ");
        }
        return check;
    }

    @Override
    public Boolean addUnitInCoverSheet(AddUnitFrameHeightForm addForm) {
        Boolean check = false;
        try{
            PriceMaterial pm = new PriceMaterial();
            pm.setPrice(addForm.getPrice());
            HeightMaterial h= new HeightMaterial();
            h.setId(addForm.getIdHeight());
            pm.setHeightMaterial(h);
            UnitMeasure unit = new UnitMeasure();
            unit.setId(addForm.getIdUnit());
            pm.setUnitMeasure(unit);
            FrameMaterial fm = new FrameMaterial();
            fm.setId(addForm.getIdFrame());
            pm.setFrameMaterial(fm);
            AccountSercurity accountSercurity = new AccountSercurity();
            pm.setModified_by(accountSercurity.getUserName());
            pm.setCreated_by(accountSercurity.getUserName());
            pm.setModified_date(LocalDate.now());
            priceMaterialRepository.save(pm);
            check =true;
        }catch (Exception e){
            throw new RuntimeException("Không thêm được ! ");
        }
        return check;
    }

    @Override
    public List<GetAllMaterialVO> getAllMaterial() {
        List<GetAllMaterialVO> list= new ArrayList<>();
        try{
            list = materialRepository.getAllMaterial();
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public List<GetAllMaterialVO> getAllCoverSheet() {
        List<GetAllMaterialVO> list= new ArrayList<>();
        try{
            list = materialRepository.getAllCoverSheet();
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public List<UnitMeasureVO> getUnitsByMaterial(Long id) {

        List<UnitMeasureVO> list= new ArrayList<>();
        try{
            list = materialRepository.getUnitSByMaterial(id);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public List<UnitMeasureVO> getUnitsByCoverSheet(Long id) {
        List<UnitMeasureVO> list= new ArrayList<>();
        try{
            list = materialRepository.getUnitSByCoverSheet(id);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public List<GetAllMaterialVO> getMaterialByUnit(Long id) {
        List<GetAllMaterialVO> list= new ArrayList<>();
        try{
            list = materialRepository.getMaterialByUnit(id);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public List<GetAllMaterialVO> getCoverSheetByUnit(Long id) {
        List<GetAllMaterialVO> list= new ArrayList<>();
        try{
            list = materialRepository.getCoverSheetByUnit(id);
        }catch (Exception e){
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }
}
