package com.imooc.sell.dao;

import com.imooc.sell.dataObject.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    @Autowired
    private OrderMasterRepository repository;
    private final String OPENID="123123";
    @Test
    public void Findtest(){
        PageRequest pageRequest=new PageRequest(0,10);
        Page<OrderMaster> result=repository.findByOrderId("123123",pageRequest);
        System.out.println(result.getContent());
    }

    @Test
    public void SaveTest(){
        OrderMaster orderMaster=new OrderMaster();

        orderMaster.setOrderStatus(0);
        orderMaster.setPayStatus(0);
        orderMaster.setBuyerOpenid("99999");
        orderMaster.setBuyerPhone("12321");
        orderMaster.setBuyerAddress("kkk");
        orderMaster.setBuyerName("zs");
        orderMaster.setOrderAmount(new BigDecimal(100));
        repository.save(orderMaster);


    }

}