package com.imooc.sell.controller;

import com.imooc.sell.Utils.ResultVOUtil;
import com.imooc.sell.VO.ProductVO;
import com.imooc.sell.VO.ProductInfoVO;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productServiceservice;//查商品
    @Autowired
    private CategoryService categoryServiceservice;//查类目

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVO list() {
        //1.查询所有上架的商品
        List<ProductInfo> productInfoList = productServiceservice.findUpAll();
        List<Integer> categoryTypeList = new ArrayList<>();//存放商品的类型
        ProductInfoVO productInfoVO = new ProductInfoVO();
        //2.查询类目(一次性查询)
        for (ProductInfo p : productInfoList) {
            categoryTypeList.add(p.getCategoryType());
        }
        List<ProductCategory> productCategoryList = categoryServiceservice.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装
        List<ProductVO> productVOList = new ArrayList<>();//按类型存放商品

        for (ProductCategory p : productCategoryList) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(p.getCategoryName());
            productVO.setCategoryType(p.getCategoryType());

            List<ProductInfoVO> productInfoVOS = new ArrayList<>();//存放商品详细信息
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(p.getCategoryType())) {
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOS.add(productInfoVO);//按类型分类商品
                }
                productVO.setProduInfoVOList(productInfoVOS);
            }
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    /**
     * 练习
     * @return
     */
    @GetMapping("/list2")
    public ResultVO list2() {
        //进行pojo到vo的转换
        //1.查询已经上线的商品
        List<ProductInfo> productInfoList = productServiceservice.findUpAll();

        //2.查询类目
        List<Integer> cateList = new ArrayList<>();
        for (ProductInfo p : productInfoList) {
            if (p.getCategoryType() != null) {
                cateList.add(p.getCategoryType());
            }
        }
        List<ProductCategory> productCategories = categoryServiceservice.findByCategoryTypeIn(cateList);

        //3.数据精品装
        ResultVO resultVO = new ResultVO();
        List<ProductVO> productVOS = new ArrayList<>();
        List<ProductInfoVO> productInfoVOS = new ArrayList<>();

        for (ProductCategory productCategory : productCategories) {
            ProductVO productVO = new ProductVO();
            productVO.setCategoryType(productCategory.getCategoryType());
            productVO.setCategoryName(productCategory.getCategoryName());
            for (ProductInfo productInfo : productInfoList) {
                if (productInfo.getCategoryType().equals(productVO.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            }
            productVO.setProduInfoVOList(productInfoVOS);
            productVOS.add(productVO);
        }
        resultVO.setMsg("成功");
        resultVO.setCode(0);
        resultVO.setData(productVOS);
        return ResultVOUtil.success(resultVO);

    }
}
