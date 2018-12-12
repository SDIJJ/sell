package com.imooc.sell.service.impl;

import com.imooc.sell.dao.OrderMasterRepository;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellExeception;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyServiceImpl implements BuyerService {
    @Autowired
    private OrderService orderService;

    //买家查询订单
    @Override
    public OrderDto findOrderOne(String openid, String orderId) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            log.error("[取消订单]查不到该订单 orderId={}", orderDto);
            throw new SellExeception(ResultEnum.ORDER_NOT_EXIST);

        }
        if (!orderDto.getBuyerOpenid().equals(openid)) {
            log.error("[查询订单]订单的openID不一致 , openid={}", orderId);
            throw new SellExeception(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }

    //买家取消订单
    @Override
    public OrderDto cannelOrder(String openid, String orderId) {
        OrderDto orderDto = orderService.findOne(orderId);
        if (orderDto == null) {
            return null;
        }
        if (!orderDto.getBuyerOpenid().equals(openid)) {
            log.error("[取消订单]订单的openID不一致 , openid={}", orderId);
            throw new SellExeception(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDto;
    }
}
