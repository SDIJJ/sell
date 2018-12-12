package com.imooc.sell.dao;

import com.imooc.sell.dataObject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {
    ProductInfo findByProductId(String productId);

    List<ProductInfo> findByProductStatus(Integer productStatus);
}
