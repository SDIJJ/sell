package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;

public interface BuyerService {
    //买家查询订单
    OrderDto findOrderOne(String openid , String orderId);
    //买家取消订单
    OrderDto cannelOrder(String openid , String orderId);

}
