package com.imooc.sell.controller.Dto;

import java.io.Serializable;

/**
 * 微信模板消息的Data数据
 *
 * @author: Arnold
 * @since: 2018/12/8 15:51
 * @version: v1.0.0
 */
public class WxTemplateMessageDataDto implements Serializable {
    private static final long serialVersionUID = 8678430869306410239L;
    /**
     * 微信模板Data的数据
     */
    private String value;
    /**
     * 微信模板Data的数据的颜色,默认蓝色(与微信风格统一)
     */
    private String color = "#173177";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
