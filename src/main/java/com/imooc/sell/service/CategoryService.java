package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer categeryId);
    List<ProductCategory> findAll();
    void save(ProductCategory productCategory);
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
