package com.imooc.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
//todo 注解问题 待解决
//@ConfigurationProperties(prefix = "projectUrl")
public class ProjectUrlConfig {
    //微信公众平台授权url
    private String wechatMpAuthorize="http://jhn.natapp1.cc";
    //微信开放平台授权url
    private String wechatOpenAuthorize="http://jhn.natapp1.cc";
    //点餐系统url
    private String sell="http://jhn.natapp1.cc";
}
