package com.imooc.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell.Utils.EnumUtil;
import com.imooc.sell.Utils.serializer.Date2LongSerializer;
import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDto {
    private String orderId;
    /**买家姓名 */
    private String buyerName;
    /**买家电话 */
    private String buyerPhone;
    /** 买家地址*/
    private String buyerAddress;
    /** 买家微信openid*/
    private String buyerOpenid;
    /** 订单金额*/
    private BigDecimal orderAmount;
    /** 订单状态*/
    private Integer orderStatus ;
    /** 支付状态*/
    private Integer payStatus ;
    /**创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    /**修改时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    List<OrderDetail> orderDetailList;
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(this.orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(this.payStatus,PayStatusEnum.class);
    }
}
