package com.imooc.sell.controller;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.config.WechatMpConfig;
import com.imooc.sell.controller.Dto.TestDto;
import com.imooc.sell.controller.Dto.WxTemplateMessageDataDto;
import com.imooc.sell.controller.Dto.WxTemplateMessageDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellExeception;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {


    public void RestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getForObject();
        URI.create("/中文/");
        RequestEntity requestEntity;
    }


    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WxMpService wxOpenService;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/test00")
    @ResponseBody
    public List<TestDto> teskt() {
        TestDto dto = new TestDto();
        dto.setName("congcong");
        TestDto dto1 = new TestDto();
        dto1.setName("congcongvv");
        TestDto dto2 = new TestDto();
        dto2.setName("vvv");
        List<TestDto> list = new LinkedList<>();
        list.add(dto);
        list.add(dto1);
        list.add(dto2);
        System.out.println(list);

        return list;
    }

    @GetMapping("/test")
    @ResponseBody
    public WxTemplateMessageDto test() {
        WxTemplateMessageDto dto = new WxTemplateMessageDto();
        dto.setTemplate_id("wwww8888");
        dto.setTousesr("8888");
        dto.setUrl("http://www.ww.com");
        WxTemplateMessageDataDto o = new WxTemplateMessageDataDto();
        o.setColor("#1975");
        o.setValue("巧克力");

        Map<String, WxTemplateMessageDataDto> dee = new HashMap<>();
        dee.put("参数1", o);
        WxTemplateMessageDataDto o1 = new WxTemplateMessageDataDto();
        o.setColor("#1975");
        o.setValue("巧克力");
        dee.put("参数2", o1);
        dto.setData(dee);
        return dto;
    }
    @GetMapping("/jhn")
    public void jhn(){
        RestTemplate template=new RestTemplate();
        String msg=template.getForObject("http://www.baidu.com",String.class);
        System.out.println(msg);
    }


    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
        //1.配置
        //2.调用方法
        String url = projectUrlConfig.getWechatMpAuthorize() + "/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_BASE, URLEncoder.encode(returnUrl));
        //log.info("[微信网页授权] 获取code,resut={}",result);
        return "redirect:" + redirectUrl;

    }

    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] {}", e);
            throw new SellExeception(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openid;
    }

    @GetMapping("/qrAuthorize")
    public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
        String url = projectUrlConfig.getWechatOpenAuthorize() + "/sell/wechat/quUserInfo";
        String redirectUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
        //log.info("[微信网页授权] 获取code,resut={}",result);
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/quUserInfo")
    public String qrUserInfo(@RequestParam("code") String code,
                             @RequestParam("state") String returnUrl) {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxOpenService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("[微信网页授权] {}", e);
            throw new SellExeception(ResultEnum.WECHAT_MP_ERROR.getCode(), e.getError().getErrorMsg());
        }
        String openid = wxMpOAuth2AccessToken.getOpenId();
        return "redirect:" + returnUrl + "?openid=" + openid;
    }
}
