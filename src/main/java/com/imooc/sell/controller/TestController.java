package com.imooc.sell.controller;

import com.imooc.sell.controller.Dto.WxTemplateMessageDataDto;
import com.imooc.sell.controller.Dto.WxTemplateMessageDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @author: Arnold
 * @since: 2018/12/11 21:59
 * @version: v1.0.0
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @ResponseBody
    @RequestMapping("/test")
    public WxTemplateMessageDto test(){
        WxTemplateMessageDto dto=new WxTemplateMessageDto();
        dto.setTemplate_id("wwww8888");
        dto.setTousesr("8888");
        dto.setUrl("htto;;;jjj");
        WxTemplateMessageDataDto o=new WxTemplateMessageDataDto();
        o.setColor("wwww");
        o.setValue("hhhh");
        dto.getData().put("eee",o);
        dto.getData().put("jhn",o);
        return dto;
    }
}
