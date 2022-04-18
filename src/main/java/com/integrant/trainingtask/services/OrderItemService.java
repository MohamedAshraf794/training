package com.integrant.trainingtask.services;

import com.integrant.trainingtask.DTO.ProductQuantity;
import com.integrant.trainingtask.models.Order;
import com.integrant.trainingtask.models.OrderItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrderItemService implements IOrderItemService{
    @Override
    public Set<OrderItem> createOrderItems(List<ProductQuantity> products, Order order) {
        List<OrderItem> orderItemList =new ArrayList<>(products.size());
        for (ProductQuantity p :products
             ) {
            OrderItem o =new OrderItem();
            o.setProductId(p.getId());
            o.setQuantity(p.getQuantity());
            o.setOrder(order);
            orderItemList.add(o);
        }
        Set<OrderItem> itemsSet = new HashSet<>(orderItemList);
        return itemsSet;
    }
}
