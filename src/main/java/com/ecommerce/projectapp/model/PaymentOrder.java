package com.ecommerce.projectapp.model;

import com.ecommerce.projectapp.domain.PaymentMethod;
import com.ecommerce.projectapp.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod;

    private String paymentLinkId;

    @ManyToOne
    private User user;

    // We're still using Set for flexibility, though typically it will be a single order
    @OneToMany
    private Set<Order> orders = new HashSet<>();

    // Timestamp when payment was completed
    private java.time.LocalDateTime paymentCompletedAt;
}