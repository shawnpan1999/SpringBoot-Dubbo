package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.consumer.entity.OrderModel;
import com.dubbo.entity.Order;
import com.dubbo.entity.Product;
import com.dubbo.service.OrderService;
import com.dubbo.service.ProductService;
import com.dubbo.util.ResultEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Reference
    private ProductService productService;


    public OrderModel orderToOrderModel(Order order) {
        ResultEntity tempResult = productService.getProduct(order.getProductId());
        String tempName = ((Product)tempResult.getData().get("product")).getProductName();
        OrderModel orderModel = new OrderModel(order.getId(), tempName, order.getAmount(), order.getUnitPrice(), order.getTotalPrice(), order.getCreateDate(), order.getStatus());
        return orderModel;
    }

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
            ResultEntity orderListResult = orderService.listOrder(userId);
            List<Order> tempOrders = (List<Order>)orderListResult.getData().get("orders");
            List<OrderModel> orders = new ArrayList<>();
            for (Order order : tempOrders) {
                orders.add(orderToOrderModel(order));
            }
            ResultEntity result = new ResultEntity();
            result.setCode(0);
            result.getData().put("orders", orders);
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
            ResultEntity result = new ResultEntity();
            Order orderTemp = (Order)orderService.payOrder(id).getData().get("order");
            result.setCode(0);
            result.getData().put("order", orderToOrderModel(orderTemp));
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
            ResultEntity result = new ResultEntity();
            ResultEntity resultTemp = orderService.finishOrder(id);
            Order orderTemp = (Order)resultTemp.getData().get("order");
            result.setCode(0);
            result.getData().put("order", orderToOrderModel(orderTemp));
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
            ResultEntity result = new ResultEntity();
            Order orderTemp = (Order)orderService.cancelOrder(id).getData().get("order");
            result.setCode(0);
            result.getData().put("order", orderToOrderModel(orderTemp));
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
