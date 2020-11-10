package com.dubbo.order.dao;

import com.dubbo.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderDAO {
    String TABLE_NAME = "orders";
    String INSERT_FIELDS = " userId, productId, amount, unitPrice, totalPrice, createDate, status ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{userId},#{productId},#{amount},#{unitPrice},#{totalPrice},#{createDate},#{status})"})
    int addOrder(Order order);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Order selectById(int id);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where userId=#{userId}"})
    List<Order> selectByUserId(int userId);

    @Update({"update ", TABLE_NAME, " set status=#{status} where id=#{id}"})
    void updateStatusById(int id, int status);

    @Update({"update ", TABLE_NAME, " set status=#{status}, finishDate=#{finishDate} where id=#{id}"})
    void updateStatusAndFinishDateById(int id, int status, int finishDate);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
