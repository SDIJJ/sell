package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

public interface PayService {
    PayResponse create(OrderDto orderDto);
    PayResponse notify(String notfiyData);
    RefundResponse refund(OrderDto orderDto);

}
