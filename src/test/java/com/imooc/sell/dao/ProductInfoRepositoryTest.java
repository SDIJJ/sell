package com.imooc.sell.dao;

import com.imooc.sell.dataObject.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

import javax.print.attribute.standard.PrinterInfo;

import java.math.BigDecimal;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository repository;
    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123123");
        productInfo.setProductName("可乐2");
        productInfo.setCategoryType(2);
        productInfo.setProductStatus(1);
        productInfo.setProductPrice(new BigDecimal(3));
        productInfo.setProductStock(100);
        this.repository.save(productInfo);
    }
    @Test
    public void findOne(){
        System.out.println(this.repository.findByProductId("123123"));
    }
}