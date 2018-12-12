package com.imooc.sell.dao;

import com.imooc.sell.dataObject.SellerInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Test
    public void findOne(){
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setOpenid("1111");
        sellerInfo.setUsername("彤彤");
        sellerInfo.setPassword("123123");
        sellerInfo.setSellerId("123333333");
        sellerInfoRepository.save(sellerInfo);

    }

}