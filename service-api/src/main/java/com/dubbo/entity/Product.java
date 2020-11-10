package com.dubbo.entity;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String productName;
    private int stock;
    private int sales;
    private float price;
    private String imageUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Product() {

    }

    public Product(String productName, int stock, float price, String imageUrl) {
        this.productName = productName;
        this.stock = stock;
        this.sales = 0;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String productName, int stock, int sales, float price, String imageUrl) {
        this.productName = productName;
        this.stock = stock;
        this.sales = sales;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
