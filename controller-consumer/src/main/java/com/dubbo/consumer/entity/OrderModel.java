package com.dubbo.consumer.entity;

import java.io.Serializable;
import java.util.Date;

public class OrderModel implements Serializable {
    private int id;
    private String productName;
    private int amount;
    private float unitPrice;
    private float totalPrice;
    private Date createDate;
    private Date finishDate;
    private int status;
    private String statusName;

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public OrderModel() {

    }

    public OrderModel(int id, String productName, int amount, float unitPrice, float totalPrice, Date createDate, int status) {
        this.id = id;
        this.productName = productName;
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createDate = createDate;
        this.status = status;
        if (status == 0) {
            this.statusName = "未付款";
        } else if (status == 1) {
            this.statusName = "已付款";
        } else if (status == 2) {
            this.statusName = "已完成";
        } else if (status == -1) {
            this.statusName = "已取消";
        }
    }
}
