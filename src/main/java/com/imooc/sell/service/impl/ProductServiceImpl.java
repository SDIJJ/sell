package com.imooc.sell.service.impl;

import com.imooc.sell.dao.ProductInfoRepository;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellExeception;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        return this.repository.findByProductId(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return this.repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {

        return this.repository.findAll(pageable);
    }

    @Override
    public void save(ProductInfo productInfo) {
        this.repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = repository.findByProductId(cartDto.getProductId());
            productInfo.setProductStock(productInfo.getProductStock() + cartDto.getProductQuantity());
            repository.save(productInfo);
            System.out.println("######### ");
        }
    }

    @Override
    @Transactional
    public void descreaseStock(List<CartDto> cartDtoList) {
        for (CartDto cartDto : cartDtoList) {
            ProductInfo productInfo = this.repository.findByProductId(cartDto.getProductId());
            if (productInfo == null) {
                throw new SellExeception(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() - cartDto.getProductQuantity();
            if (result < 0) {
                throw new SellExeception(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            this.repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.getOne(productId);
        if (productInfo == null) {
            throw new SellExeception(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus()==ProductStatusEnum.DOWN.getCode()){
            productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            repository.save(productInfo);
        }else {
            throw new SellExeception(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        return productInfo;
    }

    @Override
    @Transactional
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.getOne(productId);
        if (productInfo == null) {
            throw new SellExeception(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatus()==ProductStatusEnum.UP.getCode()){
            productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
            repository.save(productInfo);
        }else {
            throw new SellExeception(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        return productInfo;
    }
}
