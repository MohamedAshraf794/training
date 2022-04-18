package com.integrant.trainingtask.repositories;
import com.integrant.trainingtask.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    public List<Product>findByIdInAndActivated(List<Integer> id,Integer activated);
    Page<Product> findByNameStartingWith(String name, Pageable pageable);
}
