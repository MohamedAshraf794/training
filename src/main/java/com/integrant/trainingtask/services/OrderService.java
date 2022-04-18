package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.DTO.ProductsOrderInfo;
import com.integrant.trainingtask.enums.OrderStatus;
import com.integrant.trainingtask.enums.ReserveProductsStatus;
import com.integrant.trainingtask.models.Order;
import com.integrant.trainingtask.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class OrderService implements IOrderService {
    private OrderRepository orderRepository;
    private IProductService productService;
    private IOrderItemService orderItemService;

    @Autowired
    public OrderService(OrderRepository orderRepository, IProductService productService,
                        IOrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    @Override
    public Order createOrder(List<ProductQuantity> products) {
        ProductsOrderInfo info = this.productService.getOrderProductInfo(products);
        if (info.getStatus() != ReserveProductsStatus.SUCCESSFUL_RESERVATION.getStatus())
            return null;
        Order newOrder = new Order();
        UUID uuid = UUID.randomUUID();
        newOrder.setCash(info.getOrderCash());
        newOrder.setCode(uuid.toString());
        newOrder.setStatus(OrderStatus.CREATED.getStatus());
        newOrder.setUserId(1);
        newOrder.setOrderItems(this.orderItemService.createOrderItems(products, newOrder));
        return this.orderRepository.save(newOrder);
    }

    @Override
    public Order deliverOrder(Integer id) {
        Order o = this.orderRepository.findById(id).orElseThrow(NoSuchElementException::new);
        o.setStatus(OrderStatus.DELIVERD.getStatus());
        return this.orderRepository.save(o);
    }

    @Override
    public Double calculateGMV() {
        return this.orderRepository.sumOrderCash();
    }


}
