package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.models.Order;
import com.integrant.trainingtask.models.OrderItem;

import java.util.List;
import java.util.Set;

public interface IOrderItemService {
    public Set<OrderItem> createOrderItems(List<ProductQuantity> products, Order order);
}
