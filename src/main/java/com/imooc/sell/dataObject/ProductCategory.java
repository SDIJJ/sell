package com.imooc.sell.dataObject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
@Table(name = "product_category")
public class ProductCategory {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Integer categoryId;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "category_type")
    private Integer categoryType;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;

    public ProductCategory() {
    }
}
