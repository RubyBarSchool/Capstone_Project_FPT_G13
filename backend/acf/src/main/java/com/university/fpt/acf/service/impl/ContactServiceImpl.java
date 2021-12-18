package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.*;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.ContactService;
import com.university.fpt.acf.vo.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Convert;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ContactServiceImpl implements ContactService {

    public static final int COLUMN_INDEX_STT = 0;
    public static final int COLUMN_INDEX_PRODUCT = 1;
    public static final int COLUMN_INDEX_COUNT_PRODUCT = 2;
    public static final int COLUMN_INDEX_LENGTH_PRODUCT = 3;
    public static final int COLUMN_INDEX_WIDTH_PRODUCT = 4;
    public static final int COLUMN_INDEX_HEIGHT_PRODUCT = 5;
    public static final int COLUMN_INDEX_MATERIAL = 6;
    public static final int COLUMN_INDEX_FRAME = 7;
    public static final int COLUMN_INDEX_UNIT = 8;
    public static final int COLUMN_INDEX_COUNT_MATERIAL = 9;
    public static final int COLUMN_INDEX_NOTE_MATERIAL = 10;
    public static final int COLUMN_INDEX_COMPANY = 11;
    public static final int COLUMN_INDEX_PRICE = 12;
    public static final int COLUMN_INDEX_MONEY = 13;
    public static final int COLUMN_INDEX_NOTE_PRODUCT = 14;


    @Autowired
    private PriceMaterialRepository priceMaterialRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ContactCustomRepository contactCustomRepository;

    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

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
                    if (priceMaterial == null) {
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
                    valuePriceMaterial += (Integer.parseInt(priceMaterial.getPrice()) * fileMaterialVO.getCountMaterial());
                    productMaterials.add(productMaterial);
                }
                product.setProductMaterials(productMaterials);
                product.setPrice(valuePriceMaterial * fileProductVO.getCountProduct() + "");
                products.add(product);
            }
            contact.setProducts(products);
            if (products.size() == 0) {
                throw new RuntimeException("Hợp đồng lỗi không tồn tại bất kì sản phẩm nào");
            }
            contact.setNumberFinish("0/" + products.size());
            contactRepository.saveAndFlush(contact);

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for (String s : accountAdmin) {
                if (s.equals(accountSercurity.getUserName())) {
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" tạo mới một hợp đồng");
                notification.setPath("/contact");
                HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

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
    public List<ContactVO> searchContactMmoney(ContactInSearchForm contactInSearchForm) {
        List<ContactVO> contactVOS = new ArrayList<>();
        try {
            contactVOS = contactCustomRepository.searchContactMmoney(contactInSearchForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return contactVOS;
    }

    @Override
    public List<ContactProductionVO> searchContactProduction() {
        List<ContactProductionVO> contactVOS = new ArrayList<>();
        try {
            contactVOS = contactCustomRepository.searchContactProduction();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return contactVOS;
    }

    @Override
    public List<ContactVO> searchContactNotDone() {
        List<ContactVO> contactVOS = new ArrayList<>();
        try {
            contactVOS = contactRepository.getContactNotDone();
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
    public List<SearchContactDetailVO> searchContactDetail(SearchContactDetailForm searchContactDetailForm) {
        List<SearchContactDetailVO> SearchContactDetailVOS = new ArrayList<>();
        try {
            SearchContactDetailVOS = contactCustomRepository.searchContactDetail(searchContactDetailForm);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return SearchContactDetailVOS;
    }

    @Override
    public List<MaterialInContactDetailVO> getMaterialInProduct(Long idProduct) {
        List<MaterialInContactDetailVO> materialInContactDetailVOS = new ArrayList<>();
        try {
            materialInContactDetailVOS = contactRepository.getMaterialInProduct(idProduct);
        } catch (Exception e) {
            throw new RuntimeException("Không thể lấy được vật liệu theo sản phẩm trong hợp đồng");
        }
        return materialInContactDetailVOS;
    }

    @Override
    public int getTotalSearchContactDetail(SearchContactDetailForm searchContactDetailForm) {
        if (searchContactDetailForm.getTotal() != null && searchContactDetailForm.getTotal() != 0) {
            return searchContactDetailForm.getTotal().intValue();
        }
        int total = 0;
        try {
            total = contactCustomRepository.getTotalSearchContactDetail(searchContactDetailForm);
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

    @Override
    public List<GetCreateContactVO> searchCreateContact(SearchCreateContactFrom searchForm) {
        List<GetCreateContactVO> list = new ArrayList<>();
        try {
            list = contactCustomRepository.searchCreateContact(searchForm);
        } catch (Exception e) {
            throw new RuntimeException("Error contact repository " + e.getMessage());
        }
        return list;
    }

    @Override
    public int totalSearchCreateContact(SearchCreateContactFrom search) {
        int size = 0;
        try {
            size = contactCustomRepository.totalSearchCreateContact(search);
        } catch (Exception e) {
            throw new RuntimeException("Error contact repository " + e.getMessage());
        }
        return size;
    }

    @Override
    @Transactional
    public Boolean updateContact(UpdateContractForm updateForm) {
        Boolean check = false;
        try {
            AccountSercurity accountSercurity = new AccountSercurity();
            Contact c = contactRepository.getContactByID(updateForm.getId());
            c.setModified_by(accountSercurity.getUserName());
            c.setModified_date(LocalDate.now());
            c.setName(updateForm.getName());
            c.setDateFinish(updateForm.getDateFinish());
            contactRepository.save(c);
            check = true;

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for (String s : accountAdmin) {
                if (s.equals(accountSercurity.getUserName())) {
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" chỉnh sửa một hợp đồng");
                notification.setPath("/contact");
                HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error contact repository " + e.getMessage());
        }
        return check;
    }

    @Override
    @Transactional
    public Boolean deleteContact(Long id) {
        Boolean check = false;
        try {
            Contact c = contactRepository.getContactByID(id);
            String numberFinish = c.getNumberFinish().strip();
            if (c.getStatusDone() == -2 && numberFinish.startsWith("0")) {
                contactRepository.delete(c);
                check = true;
            }
            AccountSercurity accountSercurity = new AccountSercurity();
            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for (String s : accountAdmin) {
                if (s.equals(accountSercurity.getUserName())) {
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" xóa một hợp đồng");
                notification.setPath("/contact");
                HashMap<String, Object> dataOutPut = notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error contact repository " + e.getMessage());
        }
        return check;
    }

    @Autowired
    private AttendancesCustomRepository attendancesCustomRepository;

    @Override
    public ByteArrayInputStream exportContact(Long id) {
        try {
            Workbook xssfWorkbook = new XSSFWorkbook();
            Sheet sheet = xssfWorkbook.createSheet("Hợp đồng");
            int rowIndex = 0;
            this.writeHeader(sheet,rowIndex);
            //Write data
            rowIndex++;
            List<ExportContactVO> exportContactVOS = contactRepository.exportContactByID(id);

            int indexRowStartMerge = 1;
            for(int i = 0 ; i < exportContactVOS.size() ; i++){
                // Write data on row
                writeBook(exportContactVOS.get(i), sheet, rowIndex);
                if(i!=0){
                    if((!exportContactVOS.get(i).getIdProduct().equals(exportContactVOS.get(i-1).getIdProduct()))
                            ||(i==exportContactVOS.size()-1 && exportContactVOS.get(i).getIdProduct().equals(exportContactVOS.get(i-1).getIdProduct()))){
                        int indexLastRow = (i==exportContactVOS.size()-1)? rowIndex : rowIndex-1;
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,0,0));
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,1,1));
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,2,2));
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,3,3));
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,4,4));
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,5,5));
                        sheet.addMergedRegionUnsafe(new CellRangeAddress(indexRowStartMerge,indexLastRow,14,14));
                        indexRowStartMerge = rowIndex;
                    }
                }
                rowIndex++;
            }

            if(rowIndex!=1){
                // Write footer
                writeFooter(sheet, rowIndex,"Demo");
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            xssfWorkbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Không xuất được file hợp đồng");
        }
    }

    // Create CellStyle for header
    private static CellStyle createStyleForHeader(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 16); // font size
        font.setColor(IndexedColors.WHITE.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    // Create CellStyle for header
    private static CellStyle createStyleForBody(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 12); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    // Create CellStyle for header
    private static CellStyle createStyleForFooter(Sheet sheet) {
        // Create font
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 12); // font size
        font.setColor(IndexedColors.BLACK.getIndex()); // text color

        // Create CellStyle
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        return cellStyle;
    }

    private static CellStyle createStyleForBodyCenter(CellStyle cellStyle) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private static CellStyle createStyleForBodyLeft(CellStyle cellStyle) {
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    private static CellStyle createStyleForBodyRight(CellStyle cellStyle) {
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.RIGHT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }


    // Write header with format
    private void writeHeader(Sheet sheet, int rowIndex) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
        // Create row
        Row row = sheet.createRow(rowIndex);
        // Create cells
        Cell cell = row.createCell(COLUMN_INDEX_STT);
        sheet.setColumnWidth(COLUMN_INDEX_STT,2000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("STT");

        cell = row.createCell(COLUMN_INDEX_PRODUCT);
        sheet.setColumnWidth(COLUMN_INDEX_PRODUCT,5000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Hạng mục");

        cell = row.createCell(COLUMN_INDEX_COUNT_PRODUCT);
        sheet.setColumnWidth(COLUMN_INDEX_COUNT_PRODUCT,3000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("SLHM");

        cell = row.createCell(COLUMN_INDEX_LENGTH_PRODUCT);
        sheet.setColumnWidth(COLUMN_INDEX_LENGTH_PRODUCT,2000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Dài");

        cell = row.createCell(COLUMN_INDEX_WIDTH_PRODUCT);
        sheet.setColumnWidth(COLUMN_INDEX_WIDTH_PRODUCT,2000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Rộng");

        cell = row.createCell(COLUMN_INDEX_HEIGHT_PRODUCT);
        sheet.setColumnWidth(COLUMN_INDEX_HEIGHT_PRODUCT,2000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Cao");

        cell = row.createCell(COLUMN_INDEX_MATERIAL);
        sheet.setColumnWidth(COLUMN_INDEX_MATERIAL,5000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tên chất liệu");

        cell = row.createCell(COLUMN_INDEX_FRAME);
        sheet.setColumnWidth(COLUMN_INDEX_FRAME,5000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Thông số");

        cell = row.createCell(COLUMN_INDEX_UNIT);
        sheet.setColumnWidth(COLUMN_INDEX_UNIT,4000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Đơn vị");

        cell = row.createCell(COLUMN_INDEX_COUNT_MATERIAL);
        sheet.setColumnWidth(COLUMN_INDEX_COUNT_MATERIAL,3000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("SLVL");

        cell = row.createCell(COLUMN_INDEX_NOTE_MATERIAL);
        sheet.setColumnWidth(COLUMN_INDEX_NOTE_MATERIAL,5000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ghi chú");

        cell = row.createCell(COLUMN_INDEX_COMPANY);
        sheet.setColumnWidth(COLUMN_INDEX_COMPANY,4500);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Công ty");

        cell = row.createCell(COLUMN_INDEX_PRICE);
        sheet.setColumnWidth(COLUMN_INDEX_PRICE,5000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Đơn giá");

        cell = row.createCell(COLUMN_INDEX_MONEY);
        sheet.setColumnWidth(COLUMN_INDEX_MONEY,5000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Thành tiền");

        cell = row.createCell(COLUMN_INDEX_NOTE_PRODUCT);
        sheet.setColumnWidth(COLUMN_INDEX_NOTE_PRODUCT,10000);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ghi chú");
    }

    // Write data
    private static void writeBook(ExportContactVO exportContactVO, Sheet sheet, int rowIndex) {

        // Create row
        Row row = sheet.createRow(rowIndex);


        Cell cell = row.createCell(COLUMN_INDEX_STT);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(row.getRowNum());

        cell = row.createCell(COLUMN_INDEX_PRODUCT);
        cell.setCellStyle(createStyleForBodyLeft(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getNameProduct());

        cell = row.createCell(COLUMN_INDEX_COUNT_PRODUCT);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getCountProduct());

        cell = row.createCell(COLUMN_INDEX_LENGTH_PRODUCT);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(Integer.valueOf(exportContactVO.getLengthProduct()));

        cell = row.createCell(COLUMN_INDEX_WIDTH_PRODUCT);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(Integer.valueOf(exportContactVO.getWidthProduct()));

        cell = row.createCell(COLUMN_INDEX_HEIGHT_PRODUCT);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(Double.valueOf(exportContactVO.getHeightProduct()));

        cell = row.createCell(COLUMN_INDEX_MATERIAL);
        cell.setCellStyle(createStyleForBodyLeft(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getNameMaterial());

        cell = row.createCell(COLUMN_INDEX_FRAME);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(""+exportContactVO.getFrameLength()+"x"+exportContactVO.getFrameWidth()+"x"+exportContactVO.getFrameHeight());

        cell = row.createCell(COLUMN_INDEX_UNIT);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getNameUnit());

        cell = row.createCell(COLUMN_INDEX_COUNT_MATERIAL);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getCountMaterialInProduct());

        cell = row.createCell(COLUMN_INDEX_NOTE_MATERIAL);
        cell.setCellStyle(createStyleForBodyLeft(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getNoteMaterialInProduct());

        cell = row.createCell(COLUMN_INDEX_COMPANY);
        cell.setCellStyle(createStyleForBodyCenter(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getCompanyName());

        cell = row.createCell(COLUMN_INDEX_PRICE);
        cell.setCellStyle(createStyleForBodyRight(createStyleForBody(sheet)));
        cell.setCellValue(Integer.valueOf(exportContactVO.getPriceMaterial()));

        cell = row.createCell(COLUMN_INDEX_MONEY);
        cell.setCellStyle(createStyleForBodyRight(createStyleForBody(sheet)));
        cell.setCellFormula("J"+(rowIndex+1)+"*M" + (rowIndex+1) +"");

        cell = row.createCell(COLUMN_INDEX_NOTE_PRODUCT);
        cell.setCellStyle(createStyleForBodyLeft(createStyleForBody(sheet)));
        cell.setCellValue(exportContactVO.getNoteProduct());

        // Create cell formula
        // totalMoney = price * quantity
//        cell = row.createCell(COLUMN_INDEX_TOTAL, CellType.FORMULA);
//        cell.setCellStyle(cellStyleFormatNumber);
//        int currentRow = row.getRowNum() + 1;
//        String columnPrice = CellReference.convertNumToColString(COLUMN_INDEX_PRICE);
//        String columnQuantity = CellReference.convertNumToColString(COLUMN_INDEX_QUANTITY);
//        cell.setCellFormula(columnPrice + currentRow + "*" + columnQuantity + currentRow);
    }

    // Write footer
    private void writeFooter(Sheet sheet, int rowIndex, String note) {
        // Create row
        Row row = sheet.createRow(rowIndex);

        Cell cell = row.createCell(0, CellType.STRING);
        cell.setCellStyle(createStyleForBodyLeft(createStyleForFooter(sheet)));
        cell.setCellValue("Ghi Chú: " + note);
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 6));

        Cell cell1 = row.createCell(11, CellType.STRING);
        cell1.setCellStyle(createStyleForBodyLeft(createStyleForFooter(sheet)));
        cell1.setCellValue("TỔNG ( Chưa bao gồm VAT 10%): ");
        sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 11, 12));

        Cell cell2 = row.createCell(13, CellType.FORMULA);
        cell2.setCellStyle(createStyleForBodyRight(createStyleForFooter(sheet)));
        cell2.setCellFormula("SUM(N1:N" + rowIndex + ")");
    }

    // Auto resize column width
    private void autosizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
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
                String value = aDouble.doubleValue() + "";
                if (value.indexOf(".") == value.length() - 2 && value.charAt(value.length() - 1) == '0') {
                    value = value.substring(0, value.length() - 2);
                }
                cellValue = value;
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
