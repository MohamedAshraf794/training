package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.DTO.ProductsOrderInfo;
import com.integrant.trainingtask.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IProductService {
    public Product addProduct(Product product);

    public Product getProductById(Integer id);

    public Page<Product> searchProductByName(String name, int startIndex, int pageSize);

    public Product updateProduct(Product product);

    public Product addStock(ProductQuantity product);

    public Product deactivateProduct(Integer productId);

    public ProductsOrderInfo getOrderProductInfo(List<ProductQuantity> products);




}
