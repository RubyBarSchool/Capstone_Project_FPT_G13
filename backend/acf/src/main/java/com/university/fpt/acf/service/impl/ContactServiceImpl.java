package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.AddContactForm;
import com.university.fpt.acf.form.AttendanceNote;
import com.university.fpt.acf.form.ContactInSearchForm;
import com.university.fpt.acf.repository.ContactCustomRepository;
import com.university.fpt.acf.repository.ContactRepository;
import com.university.fpt.acf.repository.PriceMaterialRepository;
import com.university.fpt.acf.service.ContactService;
import com.university.fpt.acf.vo.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Convert;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private PriceMaterialRepository priceMaterialRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactCustomRepository contactCustomRepository;
    @Override
    public Contact addContact(AddContactForm addContactForm) {
        Contact contact = new Contact();
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            contact.setCreated_by(accountSercurity.getUserName());
            contact.setModified_by(accountSercurity.getUserName());
            contact.setName(addContactForm.getName());
            contact.setDateFinish(addContactForm.getTime());
            contact.setTotalMoney(addContactForm.getFileExcel().getPriceContact());
            contact.setNote(addContactForm.getFileExcel().getNoteContact());
            Company company = new Company();
            company.setId(addContactForm.getIdCompany());
            contact.setCompany(company);
            List<Product> products = new ArrayList<>();
            for (FileProductVO fileProductVO : addContactForm.getFileExcel().getFileProductVOList()) {
                Product product = new Product();
                product.setCreated_by(accountSercurity.getUserName());
                product.setModified_by(accountSercurity.getUserName());
                product.setContact(contact);
                product.setName(fileProductVO.getNameProduct());
                product.setCount(fileProductVO.getCountProduct());
                product.setWidth(fileProductVO.getFrameWidthProduct());
                product.setLength(fileProductVO.getFrameLengthProduct());
                product.setHeight(fileProductVO.getFrameHeightProduct());
                product.setNote(fileProductVO.getNoteProduct());
                product.setPriceInContact(fileProductVO.getPriceProduct());
                List<ProductMaterial> productMaterials = new ArrayList<>();
                Integer valuePriceMaterial = 0;
                for (FileMaterialVO fileMaterialVO : fileProductVO.getFileMaterialVOList()) {
                    PriceMaterial priceMaterial = priceMaterialRepository.getPriceMaterial(fileMaterialVO.getIdUnitMaterial()
                            , fileMaterialVO.getIdMaterial(), fileMaterialVO.getFrameWidthMaterial(),
                            fileMaterialVO.getFrameLengthMaterial()
                            , fileMaterialVO.getFrameHeightMaterial());
                    if(priceMaterial == null){
                        throw new RuntimeException("Không tồn tại material");
                    }
                    ProductMaterial productMaterial = new ProductMaterial();
                    productMaterial.setCreated_by(accountSercurity.getUserName());
                    productMaterial.setModified_by(accountSercurity.getUserName());
                    productMaterial.setCount(fileMaterialVO.getCountMaterial());
                    productMaterial.setNote(fileMaterialVO.getNoteMaterial());
                    productMaterial.setPriceAtCreateContact(fileMaterialVO.getPriceMaterial());
                    productMaterial.setPriceMaterial(priceMaterial);
                    productMaterial.setProduct(product);
                    valuePriceMaterial += (Integer.parseInt(priceMaterial.getPrice())*fileMaterialVO.getCountMaterial());
                    productMaterials.add(productMaterial);
                }
                product.setProductMaterials(productMaterials);
                product.setPrice(valuePriceMaterial*fileProductVO.getCountProduct() +"");
                products.add(product);
            }
            contact.setProducts(products);
            if(products.size() == 0 ){
                throw new RuntimeException("Hợp đồng lỗi không tồn tại bất kì sản phẩm nào");
            }
            contact.setNumberFinish("0/"+products.size());
            contactRepository.saveAndFlush(contact);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
        return contact;
    }

    @Override
    public List<ContactVO> searchContact(ContactInSearchForm contactInSearchForm) {
        List<ContactVO> contactVOS = new ArrayList<>();
        try {
            contactVOS = contactCustomRepository.searchContact(contactInSearchForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return contactVOS;
    }

    @Override
    public int getTotalSearchContact(ContactInSearchForm contactInSearchForm) {
        if (contactInSearchForm.getTotal() != null && contactInSearchForm.getTotal() != 0) {
            return contactInSearchForm.getTotal().intValue();
        }
        int total = 0;
        try {
            total = contactCustomRepository.getTotalSearchContact(contactInSearchForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public FileContactVO readFileContact(MultipartFile file) {
        try {
            FileContactVO fileContactVO = new FileContactVO();
            Workbook xssfWorkbook = this.getWorkbook(file.getInputStream(), file.getOriginalFilename());
            Sheet sheet = xssfWorkbook.getSheetAt(0);
            Iterator<Row> iterator = sheet.iterator();
            FileProductVO fileProductVO = new FileProductVO();
            Boolean checkLastRow = false;
            while (iterator.hasNext()) {
                FileMaterialVO fileMaterialVO = new FileMaterialVO();
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    Object objectValue = getCellValue(cell);
                    if (objectValue == null) {
                        continue;
                    }
                    int columnIndex = cell.getColumnIndex();
                    if (columnIndex == 0 && objectValue.toString().startsWith("Ghi chú")) {
                        fileContactVO.setNoteContact(objectValue.toString());
                        for (int i = 0; i < 17; i++) {
                            cell = cellIterator.next();
                        }
                        objectValue = getCellValue(cell);
                        fileContactVO.setPriceContact(objectValue.toString());
                        checkLastRow = true;
                        break;
                    }
                    switch (columnIndex) {
                        case 1:
                            String productName = objectValue.toString();
                            if (!productName.equals("")) {
                                fileProductVO = new FileProductVO();
                                fileProductVO.setNameProduct(productName);
                                fileContactVO.getFileProductVOList().add(fileProductVO);
                            }
                            break;
                        case 2:
                            String productCount = subString(objectValue.toString());
                            if (!productCount.equals("")) {
                                fileProductVO.setCountProduct(Integer.parseInt(productCount));
                            }
                            break;
                        case 3:
                            String productLength = subString(objectValue.toString());
                            if (!productLength.equals("")) {
                                fileProductVO.setFrameLengthProduct(productLength);
                            }
                            break;
                        case 4:
                            String productWidth = subString(objectValue.toString());
                            if (!productWidth.equals("")) {
                                fileProductVO.setFrameWidthProduct(productWidth);
                            }
                            break;
                        case 5:
                            String productHeight = subString(objectValue.toString());
                            if (!productHeight.equals("")) {
                                fileProductVO.setFrameHeightProduct(productHeight);
                            }
                            break;
                        case 6:
                            String idMaterial = subString(objectValue.toString());
                            fileMaterialVO.setIdMaterial(Long.parseLong(idMaterial));
                            break;
                        case 8:
                            String materialLength = subString(objectValue.toString());
                            fileMaterialVO.setFrameLengthMaterial(materialLength);
                            break;
                        case 9:
                            String materialWidth = subString(objectValue.toString());
                            fileMaterialVO.setFrameWidthMaterial(materialWidth);
                            break;
                        case 10:
                            String materialHeight = subString(objectValue.toString());
                            fileMaterialVO.setFrameHeightMaterial(materialHeight);
                            break;
                        case 11:
                            String materialNote = objectValue.toString();
                            fileMaterialVO.setNoteMaterial(materialNote);
                            break;
                        case 12:
                            String idCompany = subString(objectValue.toString());
                            fileMaterialVO.setIdCompanyMaterial(Long.parseLong(idCompany));
                            break;
                        case 14:
                            String idUnit = subString(objectValue.toString());
                            fileMaterialVO.setIdUnitMaterial(Long.parseLong(idUnit));
                            break;
                        case 16:
                            String coutMaterial = subString(objectValue.toString());
                            fileMaterialVO.setCountMaterial(Integer.parseInt(coutMaterial));
                            break;
                        case 17:
                            String priceMaterial = subString(objectValue.toString());
                            fileMaterialVO.setPriceMaterial(priceMaterial);
                            break;
                        case 18:
                            String priceProduct = subString(objectValue.toString());
                            if (!priceProduct.equals("")) {
                                fileProductVO.setPriceProduct(priceProduct);
                            }
                            break;
                        case 19:
                            String noteProduct = objectValue.toString();
                            if (!noteProduct.equals("")) {
                                fileProductVO.setNoteProduct(noteProduct);
                            }
                            break;

                    }
                }
                if (checkLastRow) {
                    break;
                }
                fileProductVO.getFileMaterialVOList().add(fileMaterialVO);
            }
            return fileContactVO;
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc tệp hợp đồng");
        }
    }

    private String subString(String input) {
        if (input.endsWith(".0")) {
            input = input.substring(0, input.length() - 2);
        }
        return input;
    }

    private Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Tệp hợp đồng không phải định dạng Excel");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellType();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                Double aDouble = Double.parseDouble(cellValue.toString());
                cellValue = aDouble.intValue() + "";
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}
