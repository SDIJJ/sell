package com.imooc.sell.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.imooc.sell.Utils.JsonUtil1;
import com.imooc.sell.controller.Dto.InMsgEntity;
import com.imooc.sell.controller.Dto.OutMsgEntity;
import javafx.scene.effect.PerspectiveTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Date;

/**
 * 微信消息和日志控制类
 *
 * @author: Arnold
 * @since: 2018/12/4 11:14
 * @version: v1.0.0
 */
@Controller
@RequestMapping
public class WxNewsLogApiController {
    @Autowired
    private static RestTemplate restTemplate;
    //发送模板消息的接口
    public static final String SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
    //URL验证时使用的token
    public static final String TOKEN = "123456789";
    //appid
    public static final String APPID = "wx4ca22860376cc217";
    //secret
    public static final String SECRET = "998c5fec6eede5c790e68922f7b75fd3";
    //创建菜单接口地址
    public static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    //获取access_token的接口地址
    public static final String GET_ACCESSTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //缓存的access_token
    private static String accessToken = "16_CmV5d6PcR7tly3fk0hKIlFgYc1m2Zl-6tgc1sc4l2MlWx_k9Ga09T4yBQydOdPSFiQ6q2ADTX9rIHXV_MgvLjqxUapfSWyW1GUNbznIbRwUWmxjBGl7KGudZ64eueRre8a3R62vCyVdreC58ZAVaAEAHID";
    //access_token的失效时间
    private static long expiresTime;

    /**
     * 获取accessToken
     *
     * @return
     */

    public static void main(String[] args) {
        sendTemplate(msg);
    }

    public static String getAccessToken() {
        //判断accessToken是否已经过期，如果过期需要重新获取
        if (accessToken == null || expiresTime < new Date().getTime()) {
            //发起请求获取accessToken
            String result = restTemplate.getForObject(GET_ACCESSTOKEN_URL.replace("APPID", APPID).replace("APPSECRET", SECRET), String.class);
            //把json字符串转换为json对象
            // JSONPObject json = JSON.parseObject(result);
            JsonNode node = JsonUtil1.object2Node(result);
            System.out.println("==============");
            System.out.println(node);

//            //缓存accessToken
////           accessToken = json1.getString("access_token");
//            accessToken=jsonNode.get("access_token");
//
//            //设置accessToken的失效时间
//            long expires_in = json.getLong("expires_in");
//            //失效时间 = 当前时间 + 有效期(提前一分钟)
//            expiresTime = new Date().getTime()+ (expires_in-60) * 1000;
        }
        return accessToken;
    }


    /**
     * 发送模板
     */
    public static void sendTemplate(String data) {
        //  String result = restTemplate.post(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", getAccessToken()),data);
        System.out.println(data);
        String result = restTemplate.postForObject(SEND_TEMPLATE_URL.replace("ACCESS_TOKEN", "16_CmV5d6PcR7tly3fk0hKIlFgYc1m2Zl-6tgc1sc4l2MlWx_k9Ga09T4yBQydOdPSFiQ6q2ADTX9rIHXV_MgvLjqxUapfSWyW1GUNbznIbRwUWmxjBGl7KGudZ64eueRre8a3R62vCyVdreC58ZAVaAEAHID"), data, String.class);

        System.out.println(result);
    }


    static String  msg = "{\n" +
            "       \"touser\":\"o7Uop6BKLTT5YbbfaAVuZ0ulgG6Q\",\n" +
            "       \"template_id\":\"NfNbUH5TSesDOMkWWfo4QY2kUzhPjVm4G-iLo3S7uxU\",\n" +
            "       \"data\":{\n" +
            "               \"first\": {\n" +
            "                   \"value\":\"恭喜你购买成功！\",\n" +
            "                   \"color\":\"#173177\"\n" +
            "               },\n" +
            "               \"Second\":{\n" +
            "                   \"value\":\"巧克力\",\n" +
            "                   \"color\":\"#173177\"\n" +
            "               },\n" +
            "               \"third\": {\n" +
            "                   \"value\":\"39.8元\",\n" +
            "                   \"color\":\"#173177\"\n" +
            "               },\n" +
            "               \"time\": {\n" +
            "                   \"value\":\"2014年9月22日\",\n" +
            "                   \"color\":\"#173177\"\n" +
            "               },\n" +
            "               \"remark\":{\n" +
            "                   \"value\":\"欢迎再次购买！\",\n" +
            "                   \"color\":\"#173177\"\n" +
            "               }\n" +
            "       }\n" +
            "}";


    @RequestMapping(value = "/weChat", method = RequestMethod.GET)
    @ResponseBody
    public String validate(String signature, String timestamp, String nonce, String echostr) {
        System.out.println("接入成功");
        return echostr;
//        //1. 将token、timestamp、nonce三个参数进行字典序排序
//        String[] arr = {timestamp,nonce,WeChatUtil.TOKEN};
//        Arrays.sort(arr);
//        //2. 将三个参数字符串拼接成一个字符串进行sha1加密
//        StringBuilder sb = new StringBuilder();
//        for (String temp : arr) {
//            sb.append(temp);
//        }
//        //3. 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
//        if(SecurityUtil.SHA1(sb.toString()).equals(signature)){
//            //接入成功
//            return echostr;
//        }
//        //接入失败
//        return null;
//    }
    }


    /**
     * 微信消息处理
     */
    @RequestMapping(value = "/weChat", method = RequestMethod.POST)
    @ResponseBody
    public Object handleMessage(@RequestBody InMsgEntity msg) {
        System.out.println(msg);
        System.out.println("收到一个请求");
        //创建消息响应对象
        OutMsgEntity out = new OutMsgEntity();
        //把原来的发送方设置为接收方
        out.setToUserName(msg.getFromUserName());
        //把原来的接收方设置为发送方
        out.setFromUserName(msg.getToUserName());
        //获取接收的消息类型
        String msgType = msg.getMsgType();
        //设置消息的响应类型
        out.setMsgType(msgType);
        //设置消息创建时间
        out.setCreateTime(new Date().getTime());
        //根据类型设置不同的消息数据
        if ("text".equals(msgType)) {
            out.setContent(msg.getContent());
        } else if ("image".equals(msgType)) {
            out.setMediaId(new String[]{msg.getMediaId()});
        }

        System.out.println(out);
        return out;



    }


    //TODO  我的申请
    //TODO  我的消息

}
