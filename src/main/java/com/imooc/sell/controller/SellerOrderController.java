package com.imooc.sell.controller;

import com.imooc.sell.dataObject.OrderDetail;
import com.imooc.sell.dataObject.OrderMaster;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellExeception;
import com.imooc.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {

        PageRequest pageRequest = new PageRequest(page - 1, size);
        Page<OrderDto> orderDtoPage = orderService.findList(pageRequest);
        map.put("orderDtoPage", orderDtoPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    /**
     * 取消订单
     *
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDto orderDto = orderService.findOne(orderId);
            System.out.println("###############");
            map.put("msg", ResultEnum.SUCCESS.getMessage());
            map.put("url", "/sell/seller/order/list");

        } catch (SellExeception e) {
//      if (orderDto == null) {
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.error("[卖家取消订单] 查询不到订单");
            return new ModelAndView("common/error", map);
//      }
        }
        return new ModelAndView("common/success", map);
    }
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map){
         OrderDto orderDto=new OrderDto();
        try{
             orderDto=orderService.findOne(orderId);
        }catch (SellExeception e){
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.error("[卖家查询订单] 查询不到订单");
            return new ModelAndView("common/error", map);
        }
         map.put("orderDto",orderDto);
        return  new ModelAndView("order/detail",map);
    }
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam(value = "orderId") String orderId,
                               Map<String,Object>  map){
        OrderDto orderDto=new OrderDto();
        try {
           if( (orderDto=orderService.findOne(orderId))!=null){
               orderService.finish(orderDto);

               map.put("msg", ResultEnum.SUCCESS.getMessage());
               map.put("url", "/sell/seller/order/list");

           }
        }catch (SellExeception e){
            map.put("msg", ResultEnum.ORDER_NOT_EXIST.getMessage());
            map.put("url", "/sell/seller/order/list");
            log.error("[卖家完成订单] 查询不到订单");
            return new ModelAndView("common/error", map);
        }
     return new ModelAndView("common/success",map);
    }
}
