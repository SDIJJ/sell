package com.imooc.sell.converter;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDtoConverter {
    public static OrderDto convert(OrderMaster orderMaster){
        OrderDto orderDto=new OrderDto();
        BeanUtils.copyProperties(orderMaster,orderDto);
        return orderDto;
    }
    public static List<OrderDto> convert(List<OrderMaster> orderMasters){
        List<OrderDto> orderDtos=orderMasters.stream().map(e -> convert(e)).
                collect(Collectors.toList());
        return orderDtos;
    }
}
