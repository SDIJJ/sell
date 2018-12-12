package com.imooc.sell.service.impl;

import com.imooc.sell.service.SellerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {
    @Autowired
    private SellerService sellerService;
    @Test
    public void findSellerInfoByOpenid() {
        System.out.println(sellerService.findSellerInfoByOpenid("1111"));
    }
}