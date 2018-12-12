package com.imooc.sell.service.impl;



import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl  orderServiceImpl;


    private final String BUYER_OPENID="123123";
    private final String ORDER_ID="123123";
    @Test
    public void create() {
        OrderDto orderDTO = new OrderDto();
        orderDTO.setBuyerName("廖师兄");
        orderDTO.setBuyerAddress("幕课网");
        orderDTO.setBuyerPhone("123456789012");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123123");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("111111");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);

        orderDTO.setOrderDetailList(orderDetailList);

        orderServiceImpl.create(orderDTO);
    }

    @Test
    public void findOne() {
       System.out.println( "测试########"+this.orderServiceImpl.findOne("1535537313946511624 "));

    }

    @Test
    public void findList() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<OrderDto> orderDtos=orderServiceImpl.findList("1111",pageRequest);
        System.out.println(orderDtos.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDto orderDto=orderServiceImpl.findOne(ORDER_ID);
        orderServiceImpl.cancel(orderDto);
    }

    @Test
    public void finish() {
        OrderDto orderDto=orderServiceImpl.findOne(ORDER_ID);
        orderServiceImpl.finish(orderDto);
    }

    @Test
    public void paid() {
        OrderDto orderDto=orderServiceImpl.findOne(ORDER_ID);
        orderServiceImpl.paid(orderDto);
    }
}