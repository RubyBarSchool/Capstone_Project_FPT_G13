package com.university.fpt.acf.service;

import com.university.fpt.acf.form.AddProductForm;

public interface ProductService {
    Boolean addProductInContact(AddProductForm addProductForm);
    Boolean deleteProductInContact(Long id);
}
