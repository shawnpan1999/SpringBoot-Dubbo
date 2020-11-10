package com.dubbo.entity;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int id;
    private int userId;
    private int productId;
    private int amount;
    private float unitPrice;
    private float totalPrice;
    private Date createDate;
    private Date finishDate;
    private int status;    //订单状态：0刚创建未付款，1已付款未收货，2已完成；-1已取消

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Order() {

    }

    public Order(int userId, int productId, int amount, float unitPrice, Date createDate, int status) {
        this.userId = userId;
        this.productId = productId;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.totalPrice = unitPrice * amount;
        this.createDate = createDate;
        this.status = status;
    }
}
