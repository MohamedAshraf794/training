package com.integrant.trainingtask.repositories;

import com.integrant.trainingtask.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query("select sum(cash) from Order")
    public Double sumOrderCash();
}
