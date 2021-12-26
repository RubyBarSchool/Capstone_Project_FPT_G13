package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.config.websocket.model.Notification;
import com.university.fpt.acf.config.websocket.service.NotificationService;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.AddMaterialInProductForm;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.repository.*;
import com.university.fpt.acf.service.ProductService;
import com.university.fpt.acf.vo.ProductVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMaterialRepository productMaterialRepository;

    @Autowired
    private ProductionOrderRepository productionOrderRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private AccountManagerRepository accountManagerRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;
    //************************************
    // Add production order
    //************************************
    @Override
    public Boolean addProductInContact(AddProductForm addProductForm) {
        List<String> listUsername = new ArrayList<>();
        AccountSercurity accountSercurity = new AccountSercurity();
        boolean check = false;
        boolean checkUpdate = false;
        try{
            Product product = new Product();
            if(addProductForm.getIdProduct() != null){
                product = productRepository.getProductByID(addProductForm.getIdProduct());
                productMaterialRepository.deleteByIdProduct(product.getId());
                if(product.getProductionOrder() != null ){
                    listUsername = productionOrderRepository.getUsernameByID(product.getProductionOrder().getId());
                }
                checkUpdate = true;
            }

            Contact contact = contactRepository.getContactByID(addProductForm.getIdContact());
            if(contact.getStatusDone() == 0 ){
                contact.setStatusDone(-2);
            }

            String[] number = contact.getNumberFinish().split("/");
            Integer numberDone = Integer.parseInt(number[0]);
            Integer numberTotal = Integer.parseInt(number[1])+1;
            contact.setNumberFinish(numberDone+"/"+numberTotal);
            if(addProductForm.getIdProduct() != null){
                BigDecimal bigDecimal = new BigDecimal(contact.getTotalMoney());
                BigDecimal bigDecimal2 = new BigDecimal(addProductForm.getPriceProduct());
                BigDecimal bigDecimal3 = new BigDecimal(product.getPriceInContact());
                contact.setTotalMoney(bigDecimal.subtract(bigDecimal3).add(bigDecimal2).toString());
            }else{
                BigDecimal bigDecimal = new BigDecimal(contact.getTotalMoney());
                BigDecimal bigDecimal2 = new BigDecimal(addProductForm.getPriceProduct());
                contact.setTotalMoney(bigDecimal.add(bigDecimal2).toString());
            }
            contact = contactRepository.saveAndFlush(contact);

            product.setCreated_by(accountSercurity.getUserName());
            product.setModified_by(accountSercurity.getUserName());
            product.setName(addProductForm.getNameProduct());
            product.setCount(addProductForm.getCountProduct());
            product.setWidth(addProductForm.getWidthFrame());
            product.setHeight(addProductForm.getHeightFrame());
            product.setLength(addProductForm.getLengthFrame());
            product.setNote(addProductForm.getNoteProduct());
            product.setPrice(addProductForm.getPriceProduct());
            product.setPriceInContact(addProductForm.getPriceProduct());

            product.setContact(contact);
            List<ProductMaterial> productMaterials = new ArrayList<>();
            for(AddMaterialInProductForm addMaterialInProductForm : addProductForm.getMaterials()){
                ProductMaterial productMaterial = new ProductMaterial();
                productMaterial.setCreated_by(accountSercurity.getUserName());
                productMaterial.setModified_by(accountSercurity.getUserName());
                productMaterial.setProduct(product);
                productMaterial.setCount(addMaterialInProductForm.getCount());
                productMaterial.setNote(addMaterialInProductForm.getNote());
                productMaterial.setPriceAtCreateContact(addMaterialInProductForm.getPrice());
                PriceMaterial priceMaterial = new PriceMaterial();
                priceMaterial.setId(addMaterialInProductForm.getId());
                productMaterial.setPriceMaterial(priceMaterial);
                productMaterials.add(productMaterial);
            }
            product.setProductMaterials(productMaterials);
            productRepository.saveAndFlush(product);
            check = true;

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for(String s : accountAdmin){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                if(checkUpdate){
                    notification.setContent("sửa sản phẩm trong hợp đồng ");
                }else{
                    notification.setContent("thêm sản phẩm trong hợp đồng ");
                }
                notification.setPath("/viewdetailcontact");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

            if(checkUpdate){
                for(String s : accountAdmin){
                    if(s.equals(accountSercurity.getUserName())){
                        continue;
                    }
                    Notification notification = new Notification();
                    notification.setType("success");
                    notification.setUsername(s);
                    notification.setUsernameCreate(accountSercurity.getUserName());
                    notification.setContent(" sửa sản phẩm trong hợp đồng ");
                    notification.setPath("/productionorder");
                    HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                    simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
                }
            }

            for(String s : listUsername){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" sửa một sản phẩm hợp đồng");
                notification.setPath("/viewwork");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return check;
    }
    //************************************
    //Delete product in contract
    //************************************
    @Override
    public Boolean deleteProductInContact(Long id) {
        boolean check = false;
        try{
            AccountSercurity accountSercurity = new AccountSercurity();
            Product product = productRepository.getProductByID(id);

            Contact contact = product.getContact();
            String[] number = contact.getNumberFinish().split("/");
            Integer numberDone = Integer.parseInt(number[0]);
            Integer numberTotal = Integer.parseInt(number[1])-1;
            List<String> listUsername = new ArrayList<>();

            if(product.getProductionOrder() != null ){
                listUsername = productionOrderRepository.getUsernameByID(product.getProductionOrder().getId());
            }
            if(numberTotal == 0){
                productMaterialRepository.deleteByIdProduct(id);
                productionOrderRepository.deleteProductionOrderByIdProduct(id);
                productRepository.deleteProductInContact(id);
                contactRepository.deleteContact(contact.getId());
            }else{
                contact.setNumberFinish(numberDone+"/"+numberTotal);
                contact.setModified_by(accountSercurity.getUserName());
                contact.setModified_date(LocalDate.now());

                BigDecimal bigDecimal = new BigDecimal(contact.getTotalMoney());
                BigDecimal bigDecimal3 = new BigDecimal(product.getPriceInContact());
                contact.setTotalMoney(bigDecimal.subtract(bigDecimal3).toString());
                contact = contactRepository.saveAndFlush(contact);
                productMaterialRepository.deleteByIdProduct(id);
                productionOrderRepository.deleteProductionOrderByIdProduct(id);
                productRepository.deleteProductInContact(id);
            }
            check = true;

            List<String> accountAdmin = accountManagerRepository.getUsernameAdmin();
            for(String s : accountAdmin){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" xóa một sản phẩm hợp đồng");
                notification.setPath("/viewdetailcontact");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }


            for(String s : accountAdmin){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" xóa một sản phẩm hợp đồng");
                notification.setPath("/productionorder");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

            for(String s : listUsername){
                if(s.equals(accountSercurity.getUserName())){
                    continue;
                }
                Notification notification = new Notification();
                notification.setType("success");
                notification.setUsername(s);
                notification.setUsernameCreate(accountSercurity.getUserName());
                notification.setContent(" xóa một sản phẩm hợp đồng");
                notification.setPath("/viewwork");
                HashMap<String,Object> dataOutPut =  notificationService.addNotification(notification);
                simpMessagingTemplate.convertAndSendToUser(s, "/queue/notification", dataOutPut);
            }

        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return check;
    }

    @Override
    public List<ProductVO> getProductInContact(Long idContact) {
        List<ProductVO> productVOS = new ArrayList<>();

        try{
            productVOS = productRepository.getProductInContact(idContact);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return productVOS;
    }

    @Override
    public List<ProductVO> getProductInContactAll(Long idContact) {
        List<ProductVO> productVOS = new ArrayList<>();

        try{
            productVOS = productRepository.getProductInContactAll(idContact);
        }catch (Exception ex){
            throw new RuntimeException(ex.getMessage());
        }
        return productVOS;
    }
}
