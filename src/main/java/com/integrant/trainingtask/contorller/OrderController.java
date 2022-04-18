package com.integrant.trainingtask.contorller;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.models.Order;
import com.integrant.trainingtask.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/orders")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public Order createOrder(@RequestBody List<ProductQuantity> productQuantities){
        return this.orderService.createOrder(productQuantities);
    }
    @GetMapping("/get-gmv")
    public Double getGMV(){
        return this.orderService.calculateGMV();
    }
    @PutMapping("/deliver-order")
    public Order deliverOrder(@RequestBody Map<String, String> body){
        int id = Integer.parseInt(body.get("id"));
        return this.orderService.deliverOrder(id);
    }
}
