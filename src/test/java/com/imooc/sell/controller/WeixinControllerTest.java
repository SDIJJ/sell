package com.imooc.sell.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

/**
 * @Description:
 * @author: Arnold
 * @since: 2018/12/12 15:28
 * @version: v1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinControllerTest {

     RestTemplate restTemplate=new RestTemplate();

    @Test
    public void auth() {
//        String msg=restTemplate.getForObject("http://www.baidu.com",String.class);
//        System.out.println(msg);
        System.out.println("jjjjfjfjfj");
    }
}