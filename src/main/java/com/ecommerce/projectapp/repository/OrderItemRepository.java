package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
