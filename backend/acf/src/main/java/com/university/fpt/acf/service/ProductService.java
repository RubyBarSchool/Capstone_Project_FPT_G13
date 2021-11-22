package com.university.fpt.acf.service;

import com.university.fpt.acf.entity.Product;
import com.university.fpt.acf.form.AddProductForm;
import com.university.fpt.acf.vo.ProductVO;
import com.university.fpt.acf.vo.ProductionOrderDetailVO;

import java.util.List;

public interface ProductService {
    Boolean addProductInContact(AddProductForm addProductForm);
    Boolean deleteProductInContact(Long id);
    List<ProductVO> getProductInContact(Long idContact);

    List<ProductVO> getProductInContactAll(Long idContact);
}
