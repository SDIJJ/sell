package com.imooc.sell.dao.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {
@Autowired
private  ProductCategoryMapper productCategoryMapper;
    @Test
    public void insertMap() {
        Map<String,Object> map=new HashMap<>();
        map.put("categoryName","智慧电梯");
        map.put("category_type",121);
        System.out.println(productCategoryMapper.insertMap(map));
    }
}