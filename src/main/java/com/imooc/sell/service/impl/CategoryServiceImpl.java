package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductCategoryRepository;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categeryId) {
        return this.repository.findByCategoryId(categeryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return this.repository.findAll();
    }

    @Override
    public void save(ProductCategory productCategory) {
        this.repository.save(productCategory);
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return this.repository.findByCategoryTypeIn(categoryTypeList);
    }
}
