package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.domain.OrderStatus;
import com.ecommerce.projectapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find orders by user
    List<Order> findByUserId(Long userId);

    // Find orders by status
    List<Order> findByOrderStatus(OrderStatus status);

    // Find orders in date range
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find orders by user and status
    List<Order> findByUserIdAndOrderStatus(Long userId, OrderStatus status);

    // Count orders by status
    long countByOrderStatus(OrderStatus status);

    // Get revenue in time period
    @Query("SELECT SUM(o.totalSellingPrice) FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate AND o.orderStatus = 'DELIVERED'")
    Long getTotalRevenue(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}