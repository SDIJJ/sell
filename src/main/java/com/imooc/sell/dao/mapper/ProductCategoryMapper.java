package com.imooc.sell.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface ProductCategoryMapper {
    @Insert("insert into product_category(category_name,category_type) values (#{category_name,jdbcType=VARCHAR},#{category_type,jdbcType=INTEGER)")
    int insertMap(Map<String, Object> map);

}
