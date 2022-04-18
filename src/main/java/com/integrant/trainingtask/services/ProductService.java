package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.DTO.ProductsOrderInfo;
import com.integrant.trainingtask.enums.ProductStatus;
import com.integrant.trainingtask.enums.ReserveProductsStatus;
import com.integrant.trainingtask.models.Product;
import com.integrant.trainingtask.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService implements IProductService {
    private final ProductRepository productRepo;

    @Autowired
    public ProductService(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product addProduct(Product product) {
        return this.productRepo.save(product);
    }

    @Override
    public Product getProductById(Integer id) {
        return this.productRepo.findById(id).get();
    }

    @Override
    public Page<Product> searchProductByName(String name, int startIndex, int pageSize) {
        Pageable pageInfo = PageRequest.of(startIndex, pageSize);
        return this.productRepo.findByNameStartingWith(name,pageInfo);
    }

    @Override
    public Product updateProduct(Product product) {
        this.productRepo.findById(product.getId()).orElseThrow(NoSuchElementException::new);
        return this.productRepo.save(product);
    }



    @Override
    public Product addStock(ProductQuantity product) {
        Product p = this.productRepo.findById(product.getId()).orElseThrow(NoSuchElementException::new);
        Integer newStock = p.getNumberInStock() + product.getQuantity();
        p.setNumberInStock(newStock);
        return this.updateProduct(p);
    }
    @Override
    public Product deactivateProduct(Integer productId) {
        Product p = this.productRepo.findById(productId).orElseThrow(NoSuchElementException::new);
        p.setActivated(ProductStatus.DEACTIVATED.getStatus());
        return this.updateProduct(p);
    }
    @Override
    public ProductsOrderInfo getOrderProductInfo(List<ProductQuantity> products) {
        Map<Integer, Integer> productToQuantityMap = getProductsToQuantityMap(products);
        List<Integer> productsIds = new ArrayList<>(productToQuantityMap.keySet());
        List<Product> activatedProducts = this.productRepo.findByIdInAndActivated(productsIds, ProductStatus.ACTIVATED.getStatus());
        if (activatedProducts.size() != productsIds.size()) {
            return new ProductsOrderInfo(ReserveProductsStatus.FAILED_PRODUCTS_NOT_ACTIVE.getStatus(), 0.0);
        }
        int reservationStatus = this.ReserveProducts(activatedProducts, productToQuantityMap);
        if (reservationStatus == ReserveProductsStatus.FAILED_NOT_ENOUGH_STOCK.getStatus()) {
            return new ProductsOrderInfo(reservationStatus, 0.0);
        }
        return new ProductsOrderInfo(reservationStatus,this.calculateOrderCash(activatedProducts,productToQuantityMap));
    }

    public int ReserveProducts(List<Product> products, Map<Integer, Integer> productToQuantityMap) {
        List<Product> productsAfterDeduction = getProductAfterDeduction(products, productToQuantityMap);
        if (productsAfterDeduction == null) {
            return ReserveProductsStatus.FAILED_NOT_ENOUGH_STOCK.getStatus();
        }
        this.productRepo.saveAll(productsAfterDeduction);
        return ReserveProductsStatus.SUCCESSFUL_RESERVATION.getStatus();
    }

    public Double calculateOrderCash(List<Product> products, Map<Integer, Integer> productToQuantityMap) {
        Double orderCash= 0.0;
        for (Product p : products
        ) {
            orderCash+= (p.getPrice() * productToQuantityMap.get(p.getId()));
        }
        return orderCash;
    }


    /*******************Util*************************/
    private Map<Integer, Integer> getProductsToQuantityMap(List<ProductQuantity> products) {
        Map<Integer, Integer> productsMap = new HashMap<>(products.size());
        for (ProductQuantity p : products
        ) {
            int newValue = p.getQuantity();
            if (productsMap.containsKey(p.getId())) {
                newValue += productsMap.get(p.getId()) + p.getQuantity();
            }
            productsMap.put(p.getId(), newValue);
        }
        return productsMap;
    }

    public List<Product> getProductAfterDeduction(List<Product> products, Map<Integer, Integer> productToQuantityMap) {
        for (Product p : products
        ) {
            Integer quantity = productToQuantityMap.get(p.getId());
            int newQuantity = p.getNumberInStock() - quantity;
            if (newQuantity < 0)
                return null;
            p.setNumberInStock(newQuantity);
        }
        return products;
    }
}
