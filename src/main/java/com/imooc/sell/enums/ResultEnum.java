package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    PARAM_ERROR(1,"参数不正确"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"商品库存不正确"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    OREDERDETAIL_NOT_EXIST(13,"订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单更新失败"),
    ORDER_DETAIL_EMPTY(16,"订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17,"订单支付失败"),
    CART_EMPTY(18,"购物车为空"),
    ORDER_OWNER_ERROR(19,"买家openId与订单不一致"),
    PRODUCT_STATUS_ERROR(20,"商品状态不正确"),
    WECHAT_MP_ERROR(21,"微信公众账号错误"),
    WECHAT_NOTIFY_MONEY_VERIFY_ERROR(22,"订单金额不一致"),
    LOGIN_FAIL(23,"登录失败"),
    LOGOUT_SUCCESS(24,"登出成功")
    ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
