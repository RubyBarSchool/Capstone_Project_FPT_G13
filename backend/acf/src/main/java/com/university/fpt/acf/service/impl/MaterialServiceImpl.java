package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.MaterialService;
import com.university.fpt.acf.vo.*;
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
    @Autowired
    private FileRepository fileRepository;

    //************************************
    // Search suggest material  with combination of fields: count, type
    //************************************
    @Override
    public List<SuggestMaterialVO> searchSuggestMaterial(MaterialSuggestFrom materialSuggestFrom) {
        List<SuggestMaterialVO> suggestMaterialVOS = new ArrayList<>();
        try {
            suggestMaterialVOS = materialCustomRepository.searchSuggestMaterial(materialSuggestFrom);
        } catch (Exception e) {
            throw new RuntimeException("Không lấy được gợi ý tấm phủ");
        }
        return suggestMaterialVOS;
    }

    //************************************
    // Search material  with combination of fields: codeMaterial, frame,group, unit, company
    //************************************
    @Override
    public List<MaterialVO> searchMaterial(SearchMaterialForm searchForm) {
        List<MaterialVO> list = new ArrayList<>();
        try {
            list = materialCustomRepository.searchMaterial(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public int totalSearchMaterial(SearchMaterialForm searchForm) {
        int size = 0;
        try {
            size = materialCustomRepository.totalSearchMaterial(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return size;
    }

    //************************************
    // Get material in add product  with combination of fields: codeMaterial, frame,group, unit, company
    //************************************
    @Override
    public List<MaterialInContactDetailVO> searchMaterialInAddProduct(SearchMaterialForm searchForm) {
        List<MaterialInContactDetailVO> list = new ArrayList<>();
        try {
            list = materialCustomRepository.searchMaterialInAddProduct(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    @Override
    public int totalSearchMaterialInAddProduct(SearchMaterialForm searchForm) {
        int size = 0;
        try {
            size = materialCustomRepository.totalSearchMaterialInAddProduct(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return size;
    }

    //************************************
    // Search coverSheet  with combination of fields: codeMaterial, frame,group, unit, company
    //************************************
    @Override
    public List<MaterialVO> searchCoverSheet(SearchMaterialForm searchForm) {
        List<MaterialVO> list = new ArrayList<>();
        try {
            list = materialCustomRepository.searchCoverSheet(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy ");
        }
        return list;
    }

    @Override
    public int totalSearchCoverSheet(SearchMaterialForm searchForm) {
        int size = 0;
        try {
            size = materialCustomRepository.totalSearchCoverSheet(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return size;
    }

    //************************************
    // Add material
    //************************************
    @Override
    @Transactional
    public Boolean addMaterial(AddMaterialForm addForm) {
        Boolean check = false;
        try {
            List<String> list = addForm.getListName();
            if(list.size() != 0){
                List<Material> materials = materialRepository.getMaterialByNameS(list);
                if(materials.size()!=0){
                    return check;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                GroupMaterial groupMaterial = groupMaterialRepository.getGroupMaterialByID(addForm.getIdGroup());
                if (groupMaterial == null) {
                    throw new RuntimeException("Không tồn tại nhóm vật liệu  ! ");
                }
                Company company = companyRespository.getCompanyById(addForm.getIdCompany());
                if (company == null) {
                    throw new RuntimeException("Không tồn tại công ty vật liệu  ! ");
                }
                CheckMaterialForm a = new CheckMaterialForm();
                a.setName(list.get(i));
                a.setIdCompany(addForm.getIdCompany());
                a.setIdGroup(addForm.getIdGroup());
                String material = materialCustomRepository.getNameMaterial(a);
                if (material == null || material.isEmpty()) {
                    Material m = new Material();
                    if (addForm.getImage() != null && !addForm.getImage().equals("")) {
                        File file = new File();
                        file.setId(addForm.getImage());
                        m.setImage(file);
                    }
                    m.setName(list.get(i));
                    m.setGroupMaterial(groupMaterial);
                    m.setCompany(company);
                    AccountSercurity accountSercurity = new AccountSercurity();
                    m.setModified_by(accountSercurity.getUserName());
                    m.setCreated_by(accountSercurity.getUserName());
                    m.setCreated_date(LocalDate.now());
                    List<PriceMaterial> listPriceMaterial = new ArrayList<>();
                    List<Long> listIdFrame = addForm.getListIdFrame();
                    for (int j = 0; j < listIdFrame.size(); j++) {
                        List<Long> listIdHeight = addForm.getListIdHeight();
                        for (int k = 0; k < listIdHeight.size(); k++) {
                            PriceMaterial pm = new PriceMaterial();
                            pm.setMaterial(m);
                            pm.setPrice(addForm.getPrice());
                            pm.setModified_by(accountSercurity.getUserName());
                            pm.setCreated_by(accountSercurity.getUserName());
                            pm.setCreated_date(LocalDate.now());
                            HeightMaterial h = new HeightMaterial();
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
                    check = true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thêm mới được ! ");
        }
        return check;
    }

    //************************************
    // Delete material
    //************************************
    @Override
    public Boolean deleteMaterial(Long id) {
        Boolean check = false;
        try {
//            Material m = materialRepository.getMaterialById(id);
//            m.setDeleted(true);
//            AccountSercurity accountSercurity = new AccountSercurity();
//            m.setModified_by(accountSercurity.getUserName());
//            m.setModified_date(LocalDate.now());
//            materialRepository.save(m);
            PriceMaterial priceMaterial = priceMaterialRepository.getPriceMaterialByIdTwo(id);
            priceMaterial.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            priceMaterial.setModified_by(accountSercurity.getUserName());
            priceMaterial.setModified_date(LocalDate.now());
            priceMaterialRepository.save(priceMaterial);
            check = true;

        } catch (Exception e) {
            throw new RuntimeException("Không Xóa được ! ");
        }
        return check;
    }

    //************************************
    // Update material
    //************************************
    @Override
    public Boolean updateMaterial(UpdateMaterialForm updateForm) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            PriceMaterial p = priceMaterialRepository.getPriceMaterialById(updateForm.getIdParameter());
            Material material = materialRepository.getMaterialById(p.getMaterial().getId());
            String fileName = "";
            if (material.getImage() != null && !material.getImage().equals("")) {
                fileName = material.getImage().getId();
            }
            if (updateForm.getImage() != null && !updateForm.getImage().equals("")) {
                File file = new File();
                file.setId(updateForm.getImage());
                material.setImage(file);
                material.setModified_by(accountSercurity.getUserName());
                material.setModified_date(LocalDate.now());
                materialRepository.save(material);
            }
            p.setPrice(updateForm.getPrice());
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            priceMaterialRepository.save(p);

            if (updateForm.getImage() != null && !updateForm.getImage().equals("")) {
                if (fileName != null && !fileName.equals("")) {
                    fileRepository.deleteByID(fileName);
                }
            }
            check = true;
        } catch (Exception e) {
            throw new RuntimeException("Không chỉnh sửa được ! ");
        }
        return check;
    }

    //************************************
    // add cover sheet
    //************************************
    @Override
    public Boolean addCoverSheet(AddMaterialForm addForm) {
        Boolean check = false;
        try {
            List<String> list = addForm.getListName();
            if(list.size() != 0){
                List<Material> materials = materialRepository.getMaterialByNameS(list);
                if(materials.size()!=0){
                    return check;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                GroupMaterial groupCoverSheet = groupMaterialRepository.getGroupCoverPlateByID(addForm.getIdGroup());
                if (groupCoverSheet == null) {
                    throw new RuntimeException("Không tồn tại nhóm tấm phủ  ! ");
                }
                Company company = companyRespository.getCompanyById(addForm.getIdCompany());
                if (company == null) {
                    throw new RuntimeException("Không tồn tại công ty tấm phủ  ! ");
                }
                CheckMaterialForm a = new CheckMaterialForm();
                a.setName(list.get(i));
                a.setIdCompany(addForm.getIdCompany());
                a.setIdGroup(addForm.getIdGroup());
                String material = materialCustomRepository.getNameCoverSheet(a);
                if (material == null || material.isEmpty()) {
                    Material m = new Material();
                    if (addForm.getImage() != null && !addForm.getImage().equals("")) {
                        File file = new File();
                        file.setId(addForm.getImage());
                        m.setImage(file);
                    }
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
                    for (int j = 0; j < listIdFrame.size(); j++) {
                        List<Long> listIdHeight = addForm.getListIdHeight();
                        for (int k = 0; k < listIdHeight.size(); k++) {
                            PriceMaterial pm = new PriceMaterial();
                            pm.setMaterial(m);
                            pm.setPrice(addForm.getPrice());
                            pm.setModified_by(accountSercurity.getUserName());
                            pm.setCreated_by(accountSercurity.getUserName());
                            pm.setCreated_date(LocalDate.now());
                            HeightMaterial h = new HeightMaterial();
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
                    check = true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thêm mới được ! ");
        }
        return check;
    }

    //************************************
    // Delete cover sheet
    //************************************
    @Override
    public Boolean deleteCoverSheet(Long id) {
        Boolean check = false;
        try {
//            Material m = materialRepository.getCoverSheetById(id);
//            m.setDeleted(true);
//            AccountSercurity accountSercurity = new AccountSercurity();
//            m.setModified_by(accountSercurity.getUserName());
//            m.setModified_date(LocalDate.now());
//            materialRepository.save(m);


            PriceMaterial priceMaterial = priceMaterialRepository.getPriceMaterialByIdTwo(id);
            priceMaterial.setDeleted(true);
            AccountSercurity accountSercurity = new AccountSercurity();
            priceMaterial.setModified_by(accountSercurity.getUserName());
            priceMaterial.setModified_date(LocalDate.now());
            priceMaterialRepository.save(priceMaterial);

            check = true;
        } catch (Exception e) {
            throw new RuntimeException("Không Xóa được ! ");
        }
        return check;
    }

    //************************************
    // Update cover sheet
    //************************************
    @Override
    public Boolean updateCoverSheet(UpdateMaterialForm updateForm) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            PriceMaterial p = priceMaterialRepository.getPriceCoverSheetById(updateForm.getIdParameter());
            Material material = materialRepository.getCoverSheetById(p.getMaterial().getId());
            String fileName = "";
            if (material.getImage() != null && !material.getImage().equals("")) {
                fileName = material.getImage().getId();
            }
            if (updateForm.getImage() != null && !updateForm.getImage().equals("")) {
                File file = new File();
                file.setId(updateForm.getImage());
                material.setImage(file);
                material.setModified_by(accountSercurity.getUserName());
                material.setModified_date(LocalDate.now());
                materialRepository.save(material);
            }
            if (p == null) {
                throw new RuntimeException("không tìm thấy để chỉnh sửa ");
            }
            p.setPrice(updateForm.getPrice());
            p.setModified_by(accountSercurity.getUserName());
            p.setModified_date(LocalDate.now());
            priceMaterialRepository.save(p);
            if (updateForm.getImage() != null && !updateForm.getImage().equals("")) {
                if (fileName != null && !fileName.equals("")) {
                    fileRepository.deleteByID(fileName);
                }
            }
            check = true;
        } catch (Exception e) {
            throw new RuntimeException("Không chỉnh sửa được ! ");
        }
        return check;
    }

    //************************************
    // Add unit in material
    //************************************
    @Override
    public Boolean addUnitInMaterial(AddUnitFrameHeightForm addForm) {
        Boolean check = false;
        try {
            List<PriceMaterial> list = priceMaterialRepository.getListPriceMaterialById(addForm.getIdMaterial());
            for (int i = 0; i < list.size(); i++) {
                PriceMaterial pm = list.get(i);
                pm.setId(null);
                pm.setPrice("0");
                UnitMeasure unit = new UnitMeasure();
                unit.setId(addForm.getIdUnit());
                pm.setUnitMeasure(unit);
                AccountSercurity accountSercurity = new AccountSercurity();
                pm.setModified_by(accountSercurity.getUserName());
                pm.setCreated_by(accountSercurity.getUserName());
                pm.setCreated_date(LocalDate.now());
                priceMaterialRepository.save(pm);
                check = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thêm được ! ");
        }
        return check;
    }

    //************************************
    // Add Unit in coversheet
    //************************************
    @Override
    public Boolean addUnitInCoverSheet(AddUnitFrameHeightForm addForm) {
        Boolean check = false;
        try {
            List<PriceMaterial> list = priceMaterialRepository.getListPriceCoverSheetById(addForm.getIdMaterial());
            for (int i = 0; i < list.size(); i++) {
                PriceMaterial pm = list.get(i);
                pm.setId(null);
                pm.setPrice("0");
                UnitMeasure unit = new UnitMeasure();
                unit.setId(addForm.getIdUnit());
                pm.setUnitMeasure(unit);
                AccountSercurity accountSercurity = new AccountSercurity();
                pm.setModified_by(accountSercurity.getUserName());
                pm.setCreated_by(accountSercurity.getUserName());
                pm.setCreated_date(LocalDate.now());
                priceMaterialRepository.save(pm);
                check = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thêm được ! ");
        }
        return check;
    }

    //************************************
    // Add frame material
    //************************************
    @Override
    public Boolean addFrameHeightMaterial(AddUnitFrameHeightForm addForm) {
        Boolean check = false;
        try {
            List<Long> listId = priceMaterialRepository.getIdPriceMaterialByFrameHeightMaterial(addForm.getIdMaterial(), addForm.getIdFrame(), addForm.getIdHeight());
            if (listId != null && listId.size() != 0) {
                throw new RuntimeException("Đã tồn tại!!! ");
            }
            List<Long> list = priceMaterialRepository.getListIdUnitPriceMaterialById(addForm.getIdMaterial());
            for (int i = 0; i < list.size(); i++) {
                PriceMaterial pm = new PriceMaterial();
                Material m = new Material();
                m.setId(addForm.getIdMaterial());
                pm.setMaterial(m);
                FrameMaterial frame = new FrameMaterial();
                frame.setId(addForm.getIdFrame());
                pm.setFrameMaterial(frame);
                HeightMaterial h = new HeightMaterial();
                h.setId(addForm.getIdHeight());
                pm.setHeightMaterial(h);
                UnitMeasure u = new UnitMeasure();
                u.setId(list.get(i));
                pm.setUnitMeasure(u);
                AccountSercurity accountSercurity = new AccountSercurity();
                pm.setModified_by(accountSercurity.getUserName());
                pm.setCreated_by(accountSercurity.getUserName());
                pm.setCreated_date(LocalDate.now());
                priceMaterialRepository.save(pm);
                check = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thêm được ! ");
        }
        return check;
    }

    //************************************
    // Add frame height coversheet
    //************************************
    @Override
    public Boolean addFrameHeightCoverSheet(AddUnitFrameHeightForm addForm) {
        Boolean check = false;
        try {
            List<Long> listId = priceMaterialRepository.getIdPriceMaterialByFrameHeightCoverSheet(addForm.getIdMaterial(), addForm.getIdFrame(), addForm.getIdHeight());
            if (listId != null && listId.size() != 0) {
                throw new RuntimeException("Đã tồn tại!!! ");
            }
            List<Long> list = priceMaterialRepository.getListIdUnitPriceCoverSheetById(addForm.getIdMaterial());
            for (int i = 0; i < list.size(); i++) {
                PriceMaterial pm = new PriceMaterial();
                Material m = new Material();
                m.setId(addForm.getIdMaterial());
                pm.setMaterial(m);
                FrameMaterial frame = new FrameMaterial();
                frame.setId(addForm.getIdFrame());
                pm.setFrameMaterial(frame);
                HeightMaterial h = new HeightMaterial();
                h.setId(addForm.getIdHeight());
                pm.setHeightMaterial(h);
                UnitMeasure u = new UnitMeasure();
                u.setId(list.get(i));
                pm.setUnitMeasure(u);
                AccountSercurity accountSercurity = new AccountSercurity();
                pm.setModified_by(accountSercurity.getUserName());
                pm.setCreated_by(accountSercurity.getUserName());
                pm.setCreated_date(LocalDate.now());
                priceMaterialRepository.save(pm);
                check = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thêm được ! ");
        }
        return check;
    }

    //************************************
    // Get all materials
    //************************************
    @Override
    public List<GetAllMaterialVO> getAllMaterial() {
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getAllMaterial();
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get all cover sheet
    //************************************
    @Override
    public List<GetAllMaterialVO> getAllCoverSheet() {
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getAllCoverSheet();
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get unit material by id material
    //************************************
    @Override
    public List<UnitMeasureVO> getUnitsByMaterial(Long id) {

        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = materialRepository.getUnitSByMaterial(id);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get unit by id coversheet
    //************************************
    @Override
    public List<UnitMeasureVO> getUnitsByCoverSheet(Long id) {
        List<UnitMeasureVO> list = new ArrayList<>();
        try {
            list = materialRepository.getUnitSByCoverSheet(id);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get material by idUnit
    //************************************
    @Override
    public List<GetAllMaterialVO> getMaterialByUnit(Long id) {
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getMaterialByUnit(id);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get cover sheet by id unit
    //************************************
    @Override
    public List<GetAllMaterialVO> getCoverSheetByUnit(Long id) {
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getCoverSheetByUnit(id);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get heights by material anh Frame
    //************************************
    @Override
    public List<HeightMaterialVO> getHeightSByMaterialAndFrame(Long idMaterial, Long idFrame) {
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getHeightSByMaterialAndFrame(idMaterial, idFrame);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get Height by coversheet and frame
    //************************************
    @Override
    public List<HeightMaterialVO> getHeightByCoverSheetAndFrame(Long idMaterial, Long idFrame) {
        List<HeightMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getHeightByCoverSheetAndFrame(idMaterial, idFrame);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get material by height frame
    //************************************
    @Override
    public List<GetAllMaterialVO> getMaterialByHeightFrame(Long idHeight, Long idFrame) {
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getMaterialByHeightFrame(idHeight, idFrame);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get coversheet by height , frame
    //************************************
    @Override
    public List<GetAllMaterialVO> getCoverSheetByHeightFrame(Long idHeight, Long idFrame) {
        List<GetAllMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getCoverSheetByHeightFrame(idHeight, idFrame);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get Frame by material and height
    //************************************
    @Override
    public List<FrameMaterialVO> getFrameByMaterialAndHeight(Long idMaterial, Long idHeight) {
        List<FrameMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getFrameByMaterialAndHeight(idMaterial, idHeight);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }

    //************************************
    // Get Frame by coverSheet and height
    //************************************
    @Override
    public List<FrameMaterialVO> getFrameByCoverSheetAndHeight(Long idCoverSheet, Long idHeight) {
        List<FrameMaterialVO> list = new ArrayList<>();
        try {
            list = materialRepository.getFrameByCoverSheetAndHeight(idCoverSheet, idHeight);
        } catch (Exception e) {
            throw new RuntimeException("Không tìm thấy! ");
        }
        return list;
    }


}
