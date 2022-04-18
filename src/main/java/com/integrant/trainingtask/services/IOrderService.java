package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.models.Order;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderService {
    public Order createOrder(List<ProductQuantity> products);
    public Order deliverOrder(Integer id);
    //Gros Merchandise Value
    public Double calculateGMV();
}
