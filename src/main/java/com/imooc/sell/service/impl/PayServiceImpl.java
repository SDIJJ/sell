package com.imooc.sell.service.impl;

import com.imooc.sell.Utils.JsonUtil;
import com.imooc.sell.Utils.MathUtil;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellExeception;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private static final String ORDER_NAME="微信点餐订单";
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    private OrderService orderService;
    @Override
    public PayResponse create(OrderDto orderDto) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOpenid(orderDto.getBuyerOpenid());
        payRequest.setOrderId(orderDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        bestPayService.pay(payRequest);
        PayResponse payResponse=bestPayService.pay(payRequest);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notfiyData) {
        //1.验证签名
        //2.支付状态
        //3.支付金额
        //4.支付人(下单人==支付人)
        PayResponse payResponse=bestPayService.asyncNotify(notfiyData);
        log.info("[微信支付] 异步通知,payResponse={}", JsonUtil.toJson(payResponse));
        //修改支付状态
        //查询订单
        OrderDto orderDto=orderService.findOne(payResponse.getOrderId());
        if(orderDto==null) {
            log.error("[微信支付] 异步通知,订单不存在,orderId={}", payResponse.getOrderId());
            throw new SellExeception(ResultEnum.ORDER_NOT_EXIST);
        }
        //检验金额(0.1 0.10)
        if(!MathUtil.equals(orderDto.getOrderAmount().doubleValue(),payResponse.getOrderAmount())){
            log.error("[微信支付] 异步通知,订单金额不一致 orderId={},微信通知金额={},系统金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDto.getOrderAmount());
            throw new SellExeception(ResultEnum.WECHAT_NOTIFY_MONEY_VERIFY_ERROR);
        }
        orderService.paid(orderDto);

        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDto orderDto) {
        RefundRequest refundRequest=new RefundRequest();
        refundRequest.setOrderId(orderDto.getOrderId());
        refundRequest.setOrderAmount(orderDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}",refundRequest);
        RefundResponse refundResponse=bestPayService.refund(refundRequest);
        log.info("[微信退款] response={}",refundResponse);
        return refundResponse;
    }
}
