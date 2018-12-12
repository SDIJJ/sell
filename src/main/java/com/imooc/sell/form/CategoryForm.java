package com.imooc.sell.form;

import lombok.Data;

import javax.persistence.Column;
@Data
public class CategoryForm {
    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;
}
