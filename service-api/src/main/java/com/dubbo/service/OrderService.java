package com.dubbo.service;

import com.dubbo.util.ResultEntity;
import java.util.Date;

public interface OrderService {
    public ResultEntity listOrder(int userId);

    public ResultEntity createOrder(int userId, int productId, int amount);

    public ResultEntity payOrder(int id);

    public ResultEntity finishOrder(int id);

    public ResultEntity cancelOrder(int id);

    public ResultEntity deleteOrder(int id);
}
