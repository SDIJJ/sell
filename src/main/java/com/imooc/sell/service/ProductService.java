package com.imooc.sell.service;

import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);
    List<ProductInfo> findUpAll();
    //分页查询
    Page<ProductInfo> findAll(Pageable pageable);
    void save(ProductInfo productInfo);
    //添加库存
    void  increaseStock(List<CartDto> cartDtoList);
    //减少库存
    void  descreaseStock(List<CartDto> cartDtoList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);

}
