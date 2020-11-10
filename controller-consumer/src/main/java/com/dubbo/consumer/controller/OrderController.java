package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.service.OrderService;
import com.dubbo.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(value = "/create")
    public String createOrder(@RequestParam(value = "userId") int userId,
                              @RequestParam(value = "productId") int productId,
                              @RequestParam(value = "amount") int amount) {
        try {
            ResultEntity result = orderService.createOrder(userId, productId, amount);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "创建订单异常").toJSONString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public String listProducts(@RequestParam(value = "userId") int userId) {
        try {
            ResultEntity result = orderService.listOrder(userId);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "获取订单异常").toJSONString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/pay")
    public String payOrder(@RequestParam(value = "id") int id) {
        try {
            ResultEntity result = orderService.payOrder(id);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "订单付款异常").toJSONString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/finish")
    public String finishOrder(@RequestParam(value = "id") int id) {
        try {
            ResultEntity result = orderService.finishOrder(id);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "订单确认异常").toJSONString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancel")
    public String cancelOrder(@RequestParam(value = "id") int id) {
        try {
            ResultEntity result = orderService.cancelOrder(id);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "订单取消异常").toJSONString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public String deleteOrder(@RequestParam(value = "id") int id) {
        try {
            ResultEntity result = orderService.deleteOrder(id);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "订单删除异常").toJSONString();
        }
    }
}
