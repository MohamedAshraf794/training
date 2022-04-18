package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.DTO.ProductsOrderInfo;
import com.integrant.trainingtask.enums.OrderStatus;
import com.integrant.trainingtask.enums.ReserveProductsStatus;
import com.integrant.trainingtask.models.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceTest {
    @Autowired
    private IOrderService orderService;
    @Test
    void createOrder() {
        List<ProductQuantity> productQuantities = new ArrayList<>();
        ProductQuantity p1= new ProductQuantity(1,2);
        ProductQuantity p2= new ProductQuantity(8,3);
        productQuantities.add(p1);
        productQuantities.add(p2);
        Order o = this.orderService.createOrder(productQuantities);
        assertNotNull(o);
    }

    @Test
    void createOrderNotActivated() {
        List<ProductQuantity> productQuantities = new ArrayList<>();
        ProductQuantity p1= new ProductQuantity(9,2);
        ProductQuantity p2= new ProductQuantity(7,3);
        productQuantities.add(p1);
        productQuantities.add(p2);
        Order o = this.orderService.createOrder(productQuantities);
        assertNull(o);
    }

    @Test
    void deliverOrder() {
        Order o =this.orderService.deliverOrder(7);
        assertEquals(OrderStatus.DELIVERD.getStatus(),o.getStatus());
    }

    @Test
    void calculateGMV() {
        Double gMW= this.orderService.calculateGMV();
        //assertEquals(2507.40,gMW);
        assertNotEquals(0.0,gMW);
    }

}