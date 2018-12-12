package com.imooc.sell.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerUserControllerTest {
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Test
    public void tess1(){
        redisTemplate.opsForValue().set("name","tongtong");
    }

}