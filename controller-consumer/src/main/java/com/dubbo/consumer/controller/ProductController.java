package com.dubbo.consumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbo.service.ProductService;
import com.dubbo.util.ResultEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Reference
    private ProductService productService;

    @ResponseBody
    @RequestMapping(value = "/add")
    public String addProduct(@RequestParam(value = "productName") String productName,
                        @RequestParam(value = "stock") int stock,
                        @RequestParam(value = "price") float price,
                        @RequestParam(value = "imageUrl") String imageUrl) {
        try {
            ResultEntity result = productService.createProduct(productName, stock, price, imageUrl);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "添加商品异常").toJSONString();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public String listProducts() {
        try {
            ResultEntity result = productService.listProducts(0, 10);
            return result.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity(1, "获取商品异常").toJSONString();
        }
    }




}
