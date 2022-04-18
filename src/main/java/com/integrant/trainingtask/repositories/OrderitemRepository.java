package com.integrant.trainingtask.repositories;


import com.integrant.trainingtask.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderitemRepository extends JpaRepository<OrderItem, Integer> {
}