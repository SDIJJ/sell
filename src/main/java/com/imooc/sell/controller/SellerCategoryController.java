package com.imooc.sell.controller;

import com.imooc.sell.dataObject.ProductCategory;
import com.imooc.sell.form.CategoryForm;
import com.imooc.sell.service.CategoryService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> productCategoryList=categoryService.findAll();
        map.put("productCategoryList",productCategoryList);
      return new ModelAndView("category/list",map);
    }
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId" ,required = false)
                              Integer categoryId ,Map<String,Object> map){
        ProductCategory productCategory=new ProductCategory();
        if(categoryId!=null){
            productCategory=categoryService.findOne(categoryId);
        }else{

        }
        map.put("productCategory",productCategory);
        return new ModelAndView("category/index",map);
    }
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult result,
                             Map<String,Object> map){
        if(result.hasErrors()){
            map.put("msg",result.getFieldError());
            map.put("url","/sell/seller/category/index");
            return  new ModelAndView("common/error",map);
        }
        ProductCategory productCategory=new ProductCategory();
        if (form.getCategoryId() != null) {
             productCategory=categoryService.findOne(form.getCategoryId());
        }
        BeanUtils.copyProperties(form,productCategory);
        categoryService.save(productCategory);

        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
