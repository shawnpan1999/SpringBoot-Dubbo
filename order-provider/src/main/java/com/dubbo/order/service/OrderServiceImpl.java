package com.dubbo.order.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.entity.Order;
import com.dubbo.entity.Product;
import com.dubbo.order.dao.OrderDAO;
import com.dubbo.service.OrderService;
import com.dubbo.service.ProductService;
import com.dubbo.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
@org.springframework.stereotype.Service
public class OrderServiceImpl implements OrderService {

    @Reference
    private ProductService productService;

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public ResultEntity listOrder(int userId) {
        ResultEntity result = new ResultEntity();
        List<Order> orders = orderDAO.selectByUserId(userId);
        if (orders.isEmpty()) {
            result.setCode(1);
            result.setMsg("没有订单");
            return result;
        }
        result.setCode(0);
        result.setMsg("总共有" + orders.size() + "个订单");
        result.getData().put("orders", orders);
        return result;
    }

    @Override
    public ResultEntity createOrder(int userId, int productId, int amount) {
        ResultEntity result = new ResultEntity();
        Product product = (Product) productService.getProduct(productId).getData().get("product");
        if (product == null) {
            result.setCode(1);
            result.setMsg("商品不存在");
            return result;
        }
        if (product.getStock() < amount) {
            result.setCode(2);
            result.setMsg("商品库存不足");
            return result;
        }
        float unitPrice = product.getPrice();
        Order order = new Order(userId, product.getId(), amount, unitPrice, new Date(), 0);

        orderDAO.addOrder(order);
        productService.updateStockSales(product.getId(), product.getStock() - amount, product.getSales() + amount);
        result.setCode(0);
        result.setMsg("创建订单成功");
        result.getData().put("order", order);
        return result;
    }

    @Override
    public ResultEntity payOrder(int id) {
        ResultEntity result = new ResultEntity();
        Order order = orderDAO.selectById(id);
        if (order == null) {
            result.setCode(1);
            result.setMsg("订单不存在");
            return result;
        }
        int state = order.getStatus();
        if (state == -1) {
            result.setCode(2);
            result.setMsg("订单已取消");
            return result;
        }
        if (state != 0) {
            result.setCode(3);
            result.setMsg("订单不可付款");
            return result;
        }
        orderDAO.updateStatusById(id, 1);
        result.setCode(0);
        result.setMsg("订单付款成功");
        result.getData().put("order", order);
        return result;
    }

    @Override
    public ResultEntity finishOrder(int id) {
        ResultEntity result = new ResultEntity();
        Order order = orderDAO.selectById(id);
        if (order == null) {
            result.setCode(1);
            result.setMsg("订单不存在");
            return result;
        }
        int state = order.getStatus();
        if (state == -1) {
            result.setCode(2);
            result.setMsg("订单已取消");
            return result;
        }
        if (state != 1) {
            result.setCode(3);
            result.setMsg("订单尚未付款");
            return result;
        }
        orderDAO.updateStatusById(id, 2);
        result.setCode(0);
        result.setMsg("订单完成");
        result.getData().put("order", order);
        return result;
    }

    @Override
    public ResultEntity cancelOrder(int id) {
        ResultEntity result = new ResultEntity();
        Order order = orderDAO.selectById(id);
        if (order == null) {
            result.setCode(1);
            result.setMsg("订单不存在");
            return result;
        }
        int state = order.getStatus();
        if (state == -1) {
            result.setCode(2);
            result.setMsg("订单已取消");
            return result;
        }
        if (state == 2) {
            result.setCode(3);
            result.setMsg("订单已经完成");
            return result;
        }
        orderDAO.updateStatusById(id, -1);
        result.setCode(0);
        result.setMsg("订单已取消");
        result.getData().put("order", order);
        return result;
    }

    @Override
    public ResultEntity deleteOrder(int id) {
        ResultEntity result = new ResultEntity();
        Order order = orderDAO.selectById(id);
        if (order == null) {
            result.setCode(1);
            result.setMsg("订单不存在");
            return result;
        }
        int state = order.getStatus();
        if (state != -1 && state!= 2) {
            result.setCode(2);
            result.setMsg("订单还未完成");
            return result;
        }
        orderDAO.deleteById(id);
        result.setCode(0);
        result.setMsg("删除订单成功");
        return result;
    }
}
