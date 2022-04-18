package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.DTO.ProductsOrderInfo;
import com.integrant.trainingtask.enums.ProductStatus;
import com.integrant.trainingtask.enums.ReserveProductsStatus;
import com.integrant.trainingtask.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private IProductService productService;

    @Test
    void addProductNormal() {
        Product p = new Product();
        p.setActivated(ProductStatus.ACTIVATED.getStatus());
        p.setNumberInStock(10);
        p.setName("Air Frier");
        p.setPrice(300.98);
        Product returnedProduct = productService.addProduct(p);
        assertEquals(p, returnedProduct);
    }

    @Test
    void addProductDeactivated() {
        Product p = new Product();
        p.setActivated(ProductStatus.DEACTIVATED.getStatus());
        p.setNumberInStock(10);
        p.setName("CocaCola");
        p.setPrice(6.5);
        Product returnedProduct = productService.addProduct(p);
        assertEquals(p, returnedProduct);
    }

    @Test
    void addProductNormalDuplicated() {
        Product p = new Product();
        p.setActivated(ProductStatus.ACTIVATED.getStatus());
        p.setNumberInStock(10);
        p.setName("flag");
        p.setPrice(200.5);
        Product returnedProduct = productService.addProduct(p);
        assertEquals(p, returnedProduct);
    }

    @Test
    void updateProduct() {
        Product p =new Product();
        p.setId(1);
        p.setNumberInStock(15);
        p.setName("AC");
        p.setPrice(200.5);
        p.setActivated(ProductStatus.ACTIVATED.getStatus());
        Product returnedProduct = productService.updateProduct(p);
        assertEquals(p, returnedProduct);
    }

    @Test
    void updateProductNotExisiting() {
        Product p =new Product();
        p.setId(50);
        p.setNumberInStock(15);
        p.setName("Crown");
        p.setPrice(200.5);
        p.setActivated(ProductStatus.ACTIVATED.getStatus());
        assertThrows(NoSuchElementException.class,()->productService.updateProduct(p));
    }

    @Test
    void addStock() {
        ProductQuantity p1 =new ProductQuantity(7,10);
        Product p = this.productService.getProductById(p1.getId());
        int newQuantityInStock = p1.getQuantity()+p.getNumberInStock();
        Product pAfterUpdate = this.productService.addStock(p1);
        assertEquals(newQuantityInStock,pAfterUpdate.getNumberInStock());
    }

    @Test
    void addNotPresentStock() {
        ProductQuantity p1 =new ProductQuantity(50,10);
        assertThrows(NoSuchElementException.class,()->this.productService.addStock(p1));
    }

    @Test
    void getOrderProductInfo() {
        List<ProductQuantity>productQuantities = new ArrayList<>();
        ProductsOrderInfo excepted = new ProductsOrderInfo(ReserveProductsStatus.SUCCESSFUL_RESERVATION.getStatus(),1504.9);
        ProductQuantity p1= new ProductQuantity(6,2);
        ProductQuantity p2= new ProductQuantity(7,3);
        productQuantities.add(p1);
        productQuantities.add(p2);
        assertEquals(this.productService.getOrderProductInfo(productQuantities),excepted);
    }
    @Test
    void getOrderProductInfoNotActivated() {
        List<ProductQuantity>productQuantities = new ArrayList<>();
        ProductsOrderInfo excepted = new ProductsOrderInfo(ReserveProductsStatus.FAILED_PRODUCTS_NOT_ACTIVE.getStatus(),0.0);
        ProductQuantity p1= new ProductQuantity(1,2);
        ProductQuantity p2= new ProductQuantity(7,3);
        productQuantities.add(p1);
        productQuantities.add(p2);
        assertEquals(this.productService.getOrderProductInfo(productQuantities),excepted);
    }
    @Test
    void getOrderProductInfoNotEnoughInStock() {
        List<ProductQuantity>productQuantities = new ArrayList<>();
        ProductsOrderInfo excepted = new ProductsOrderInfo(ReserveProductsStatus.FAILED_NOT_ENOUGH_STOCK.getStatus(),0.0);
        ProductQuantity p1= new ProductQuantity(6,15);
        ProductQuantity p2= new ProductQuantity(7,3);
        productQuantities.add(p1);
        productQuantities.add(p2);
        assertEquals(this.productService.getOrderProductInfo(productQuantities),excepted);
    }

    @Test
    void searchProductByName() {
        Page<Product> pages=this.productService.searchProductByName("A",0,5);
        assertEquals(2,pages.getTotalElements());
    }

    @Test
    void searchProductByNameNotExisited() {
        Page<Product> pages=this.productService.searchProductByName("plat",0,5);
        assertEquals(0,pages.getTotalElements());
    }
}