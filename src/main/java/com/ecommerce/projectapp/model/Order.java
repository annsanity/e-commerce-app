package com.ecommerce.projectapp.model;

import com.ecommerce.projectapp.domain.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private int totalMrpPrice;

    private int totalSellingPrice;

    private int discount;

    private int totalItem;

    private LocalDateTime orderDate = LocalDateTime.now();

    @OneToOne
    private Address shippingAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    // Method to calculate totals based on order items
    public void calculateTotals() {
        totalItem = orderItems.size();
        totalMrpPrice = orderItems.stream().mapToInt(item -> item.getMrpPrice()).sum();
        totalSellingPrice = orderItems.stream().mapToInt(item -> item.getSellingPrice()).sum();

        // Calculate discount percentage
        if (totalMrpPrice > 0) {
            discount = (int)(((totalMrpPrice - totalSellingPrice) / (double)totalMrpPrice) * 100);
        }
    }
}