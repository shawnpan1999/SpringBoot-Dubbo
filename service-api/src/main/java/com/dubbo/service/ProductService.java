package com.dubbo.service;

import com.dubbo.util.ResultEntity;

public interface ProductService {

    public ResultEntity createProduct(String productName, int stock, float price, String imageUrl);

    public ResultEntity updateStockSales(int id, int stock, int sales);

    public ResultEntity getProducts(String searchName);

    public ResultEntity listProducts(int offset, int limit);

    public ResultEntity getProduct(int id);

    public ResultEntity deleteProduct(int id);
}
