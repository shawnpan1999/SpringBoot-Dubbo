package com.dubbo.product.dao;

import com.dubbo.entity.Product;
import com.dubbo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductDAO {
    String TABLE_NAME = "product";
    String INSERT_FIELDS = " productName, stock, sales, price, imageUrl ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;
    String ORDER_BY = " id ";

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{productName},#{stock},#{sales},#{price},#{imageUrl})"})
    int addProduct(Product product);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Product selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by", ORDER_BY, "desc limit #{offset},#{limit}"})
    List<Product> selectByOffset(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where productName=#{productName}"})
    List<Product> selectByProductName(String productName);

    @Update({"update ", TABLE_NAME, " set stock=#{stock}, set sales=#{sales} where id=#{id}"})
    void updateStockAndSalesById(int id, int stock, int sales);

    @Delete({"delete from ", TABLE_NAME, " where id=#{id}"})
    void deleteById(int id);
}
