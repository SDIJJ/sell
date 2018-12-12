package com.imooc.sell.controller;

import com.imooc.sell.Utils.KeyUtil;
import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.dataObject.ProductInfo;
import com.imooc.sell.exception.SellExeception;
import com.imooc.sell.form.ProductForm;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<ProductInfo> productInfoPage = productService.findAll(pageRequest);
        map.put("productInfoPage", productInfoPage);
        map.put("size", size);
        map.put("currentPage", page);
        return new ModelAndView("product/list", map);
    }

    @GetMapping("/onSale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productService.onSale(productId);
        } catch (SellExeception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }

    @GetMapping("/offSale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productService.offSale(productId);
        } catch (SellExeception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/list");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId" ,required = false)
                                          String productId, Map<String, Object> map){
        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo=productService.findOne(productId);
            map.put("productInfo",productInfo);
            List<ProductCategory> productCategoryList=categoryService.findAll();
            map.put("productCategoryList",productCategoryList);

        }
        return new ModelAndView("product/index",map);
    }

    /**
     * 保存和更新
     * @return
     */
    @PostMapping("/save")
    //TODO 使用报错
//    @CachePut(cacheNames = "product",key="123")
    @CacheEvict(cacheNames = "product",key="123")
    public ModelAndView save(@Valid ProductForm form,

                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError());
            map.put("url","/sell/seller/product/index");
            return  new ModelAndView("common/error",map);
        }
        ProductInfo productInfo=new ProductInfo();
        try {
            if (!StringUtils.isEmpty(form.getProductId())){
                productInfo=productService.findOne(form.getProductId());
            }else{
                form.setProductId(KeyUtil.getUniqueKey());
            }
        BeanUtils.copyProperties(form,productInfo);

            productService.save(productInfo);
        }catch (SellExeception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return  new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
