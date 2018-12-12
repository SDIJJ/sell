package com.imooc.sell.controller.Dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 微信模板消息Dto
 *
 * @author: Arnold
 * @since: 2018/12/8 15:34
 * @version: v1.0.0
 */
public class WxTemplateMessageDto implements Serializable {

    private static final long serialVersionUID = -7501860942780494389L;
/*微信模板样例
 {
            "touser": "o7Uop6BKLTT5YbbfaAVuZ0ulgG6Q",
            "template_id": "o7Uop6PyhSiE3R3wWzNQcUtNSjYs",
            "url":"http://weixin.qq.com/download",
            "data": {
            "name": {
            "value": "聪聪",
                    "color": "#173177"
        },
         "groupName": {
            "value": "219交流群",
                    "color": "#173177"
        }

    }*/
    /**
     * 微信用户openId
     */
    private String tousesr;
    /**
     * 微信模板Id
     */
    private String template_id;
    /**
     * 消息详细内容的url
     */
    private String url;
    /**
     * 填充的数据
     */
    private Map<String, WxTemplateMessageDataDto> data;

    public String getTousesr() {
        return tousesr;
    }

    public void setTousesr(String tousesr) {
        this.tousesr = tousesr;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, WxTemplateMessageDataDto> getData() {
        return data;
    }

    public void setData(Map<String, WxTemplateMessageDataDto> data) {
        this.data = data;
    }
}

