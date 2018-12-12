package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductCategoryRepository;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;
    @Test
    public void findOne() {
        System.out.println(categoryService.findOne(1));
    }

    @Test
    public void findAll() {
        List<ProductCategory> list=this.categoryService.findAll();
        for(ProductCategory p:list){
            System.out.println(p);
        }
    }

    @Test
    public void save() {
        ProductCategory p=new ProductCategory();

        p.setCategoryName("女生最爱");
        p.setCategoryType(4);
        this.categoryService.save(p);
    }

    @Test
    public void findByCategoryTypeIn() {
    }
}