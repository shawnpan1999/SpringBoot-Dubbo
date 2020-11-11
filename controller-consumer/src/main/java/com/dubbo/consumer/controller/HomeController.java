package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.consumer.entity.HostHolder;
import com.dubbo.consumer.entity.OrderModel;
import com.dubbo.entity.Order;
import com.dubbo.entity.Product;
import com.dubbo.service.OrderService;
import com.dubbo.service.ProductService;
import com.dubbo.service.UserService;
import com.dubbo.util.ResultEntity;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Reference
    private UserService userService;

    @Reference
    private ProductService productService;

    @Reference
    private OrderService orderService;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value = "/")
    public String index(Model model) {
        try {
            ResultEntity productListResult = productService.listProducts(0, 10);
            List<Product> products = (List<Product>) productListResult.getData().get("products");
            model.addAttribute("products", products);
            ResultEntity orderListResult = orderService.listOrder(hostHolder.getUser().getId());
            List<Order> tempOrders = (List<Order>)orderListResult.getData().get("orders");
            List<OrderModel> orders = new ArrayList<>();
            ResultEntity tempResult;
            String tempName;
            for (Order order : tempOrders) {
                tempResult = productService.getProduct(order.getProductId());
                tempName = ((Product)tempResult.getData().get("product")).getProductName();
                OrderModel orderModel = new OrderModel(order.getId(), tempName, order.getAmount(), order.getUnitPrice(), order.getTotalPrice(), order.getCreateDate(), order.getStatus());
                orders.add(orderModel);
            }
            model.addAttribute("orders", orders);
            return "index";
        } catch (Exception e) {
            e.printStackTrace();
            return "index";
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {    //直接从注解中获得 Cookie 值
        userService.logout(ticket);
        return "redirect:/";
    }
}
