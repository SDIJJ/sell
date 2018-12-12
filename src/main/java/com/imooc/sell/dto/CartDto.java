package com.imooc.sell.dto;

import lombok.Data;

@Data
public class CartDto {
    private String productId;
    private Integer productQuantity;

    public CartDto(String productId, Integer productQuantity) {
        //商品id
        this.productId = productId;
        //商品数量
        this.productQuantity = productQuantity;
    }
}
