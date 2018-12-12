package com.imooc.sell.service;

import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService{
    /**创建订单     */
    OrderDto create(OrderDto orderDto);
    /**查询订单     */
    OrderDto findOne(String orderId);
    /**创建全部订单     */
    Page<OrderDto> findList(String buyerOpenid, Pageable pageable);
    /**取消订单     */
    OrderDto cancel(OrderDto orderDto);
    /**完成订单     */
    OrderDto finish(OrderDto orderDto);
    /**支付订单     */
    OrderDto paid(OrderDto orderDto);
    /**查询订单     */
    Page<OrderDto> findList(Pageable pageable);
}
