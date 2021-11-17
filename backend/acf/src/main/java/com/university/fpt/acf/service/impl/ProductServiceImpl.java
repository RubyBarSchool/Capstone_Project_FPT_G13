package com.university.fpt.acf.service.impl;

import com.university.fpt.acf.config.security.AccountSercurity;
import com.university.fpt.acf.entity.*;
import com.university.fpt.acf.form.AddMaterialInProductForm;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.repository.ProductRepository;
import com.university.fpt.acf.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Boolean addProductInContact(AddProductForm addProductForm) {
        AccountSercurity accountSercurity = new AccountSercurity();
        boolean check = false;
        try{
            Product product = new Product();
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
            Contact contact = new Contact();
            contact.setId(addProductForm.getIdContact());
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
        }catch (Exception e){
            throw  new RuntimeException(e.getMessage());
        }
        return check;
    }
}
