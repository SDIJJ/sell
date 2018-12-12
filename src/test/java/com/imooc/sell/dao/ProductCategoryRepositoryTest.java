package com.imooc.sell.dao;


import com.imooc.sell.dao.ProductCategoryRepository;
import com.imooc.sell.dataObject.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest() {
        List<ProductCategory> list = this.productCategoryRepository.findAll();
        for (ProductCategory p : list) {
            System.out.println(p);
        }
    }

    @Test
    public void saveTest() {
        ProductCategory p=new ProductCategory();
        p.setCategoryId(2);
        p.setCategoryName("男生最爱");
        p.setCategoryType(3);
        this.productCategoryRepository.save(p);
    }

}
