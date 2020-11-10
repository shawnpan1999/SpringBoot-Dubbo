package com.dubbo.product.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.entity.Product;
import com.dubbo.product.dao.ProductDAO;
import com.dubbo.service.ProductService;
import com.dubbo.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
@org.springframework.stereotype.Service
public class ProductServiceImpl implements ProductService {
    /**TODO: 完善 ProductServiceImpl 类
     * ProductServiceImpl 是 ProductService 的实现类，用于完成商品服务的具体业务操作。
     * 具体可以参考 UserServiceImpl 的写法。
     */

    @Autowired
    private ProductDAO productDAO;


    /**
     * 创建商品
     * @param productName 商品名称
     * @param stock 商品库存
     * @param price 商品价格
     * @param imageUrl 商品图片(url)
     * @return Result 实体，包含创建成功、失败信息；若成功 data 部分携带创建的 Product 实体
     */
    @Override
    public ResultEntity createProduct(String productName, int stock, float price, String imageUrl) {
        Product product = new Product(productName, stock, price, imageUrl);
        ResultEntity result = new ResultEntity();
        productDAO.addProduct(product);
        result.setCode(0);
        result.setMsg("创建商品成功");
        result.getData().put("product", product);
        return result;
    }

    @Override
    public ResultEntity updateStockSales(int id, int stock, int sales) {
        ResultEntity result = new ResultEntity();
        productDAO.updateStockAndSalesById(id, stock, sales);
        result.setCode(0);
        result.setMsg("修改销量库存成功");
        return result;
    }

    /**
     * 检索商品
     * @param searchName 商品名称(*可选-模糊匹配)
     * @return Result 实体，包含查询成功、失败信息；若成功 data 部分携带所有查询到的 Product 的 ArrayList
     */
    @Override
    public ResultEntity getProducts(String searchName) {
        ResultEntity result = new ResultEntity();
        List<Product> products = productDAO.selectByProductName(searchName);
        if (products.isEmpty()) {
            result.setCode(1);
            result.setMsg("没有查询到商品");
            return result;
        }
        result.setCode(0);
        result.setMsg("共查询到" + products.size() + "个商品");
        result.getData().put("products", products);
        return result;
    }

    /**
     * 查询商品
     * @param id 商品 id
     * @return Result 实体，包含查询成功失败信息，成功则塞入查询到的商品信息
     */
    @Override
    public ResultEntity getProduct(int id) {
        ResultEntity result = new ResultEntity();
        Product product = productDAO.selectById(id);
        if (product == null) {
            result.setCode(1);
            result.setMsg("获取商品失败");
            return result;
        }
        result.setCode(0);
        result.setMsg("获取商品成功");
        result.getData().put("product", product);
        return result;
    }

    @Override
    public ResultEntity listProducts(int offset, int limit) {
        ResultEntity result = new ResultEntity();
        List<Product> products = new ArrayList<>();
        products = productDAO.selectByOffset(offset, limit);
        result.setCode(0);
        result.setMsg("获取商品成功");
        result.getData().put("products", products);
        return result;
    }

    /**
     * 删除商品
     * @param id 商品 id
     * @return  Result 实体，包含删除成功、失败信息
     */
    @Override
    public ResultEntity deleteProduct(int id) {
        ResultEntity result = new ResultEntity();
        productDAO.deleteById(id);
        result.setCode(0);
        result.setMsg("删除商品成功");
        return result;
    }
}
