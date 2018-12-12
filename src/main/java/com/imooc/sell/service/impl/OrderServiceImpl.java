package com.imooc.sell.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.imooc.sell.Utils.KeyUtil;
import com.imooc.sell.converter.OrderMaster2OrderDtoConverter;
import com.imooc.sell.dao.OrderDetialRepository;
import com.imooc.sell.dao.OrderMasterRepository;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellExeception;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.service.ProductService;
import com.imooc.sell.service.WebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetialRepository orderDetialRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private PayService payService;
    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDto create(OrderDto orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        orderDTO.setOrderId(orderId);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);//总价

        //  List<CartDTO> cartDTOList = new ArrayList<>();

        //1. 查询商品（数量, 价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellExeception(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetialRepository.save(orderDetail);
//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }
        //3. 写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4. 扣库存
        List<CartDto> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDto(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productService.descreaseStock(cartDTOList);
        //发送websocket消息
        webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    public OrderDto findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        if (orderMaster == null) {
            throw new SellExeception(ResultEnum.PRODUCT_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetialRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellExeception(ResultEnum.PRODUCT_NOT_EXIST);
        }
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(orderMaster, orderDto);
        orderDto.setOrderDetailList(orderDetailList);
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasters = orderMasterRepository.findByOrderId(buyerOpenid, pageable);
        List<OrderDto> orderDtos = OrderMaster2OrderDtoConverter.convert(orderMasters.getContent());
        return new PageImpl<OrderDto>(orderDtos, pageable, orderMasters.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDto cancel(OrderDto orderDto) {
        OrderMaster orderMaster = new OrderMaster();
        //1.判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单]订单状态不正确,orderId={},orderStatue={}", orderMaster.getOrderId(), orderMaster.getPayStatus());
            throw new SellExeception(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDto, orderMaster);

        OrderMaster upOrderMaster = orderMasterRepository.save(orderMaster);
        if (upOrderMaster == null) {
            log.error("[取消订单] 跟新失败,orderMaster={} ", orderMaster);
            throw new SellExeception(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //3.返回库存
        if (CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
            log.error("[取消订单] 订单中无商品详情,orderDto={}", orderDto);
            throw new SellExeception(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream().
                map(e -> new CartDto(e.getProductId(), e.getProductQuantity())).
                collect(Collectors.toList());
        productService.increaseStock(cartDtoList);
        //4.如果已经支付,退款
        if (orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            payService.refund(orderDto);
        }
        return orderDto;
    }

    @Override
    public OrderDto finish(OrderDto orderDto) {
        //1.判断订单状态
        if (!orderDto.getPayStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单]订单状态不正确,orderId={},orderStatue={}", orderDto.getOrderId(), orderDto.getPayStatus());
            throw new SellExeception(ResultEnum.ORDER_STATUS_ERROR);
        }

        //2.修改订单状态
        orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMaster1 = orderMasterRepository.save(orderMaster);
        if (orderMaster1 == null) {
            log.error("[完成订单] 跟新失败,orderMaster={} ", orderMaster);
            throw new SellExeception(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板消息
        pushMessageService.orderStatus(orderDto);
        return orderDto;
    }

    @Override
    @Transactional
    public OrderDto paid(OrderDto orderDto) {
        //1.判断订单状态
        if (!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[支付订单]订单状态不正确,orderId={},orderStatue={}", orderDto.getOrderId(), orderDto.getPayStatus());
            throw new SellExeception(ResultEnum.ORDER_STATUS_ERROR);
        }
        //2.判断支付状态
        if (!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[支付订单] 支付状态不正确 orderDTO={}", orderDto);
            throw new SellExeception(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //3.修改支付状态
        orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        OrderMaster orderMaster1 = orderMasterRepository.save(orderMaster);
        if (orderMaster1 == null) {
            log.error("[支付订单] 跟新失败,orderMaster={} ", orderMaster);
            throw new SellExeception(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDto;
    }

    @Override
    public Page<OrderDto> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage=orderMasterRepository.findAll(pageable);
        List<OrderDto> orderDtoList=OrderMaster2OrderDtoConverter.convert(orderMasterPage.getContent());
        return new PageImpl<OrderDto>(orderDtoList,pageable,orderMasterPage.getTotalElements());

    }
}
