package com.ecommerce.projectapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User customer;

    @OneToOne
    private Order order;

    // Removed seller reference

    private LocalDateTime date = LocalDateTime.now();

    // Added transaction ID from payment provider
    private String paymentProviderTransactionId;

    // Added payment method info
    private String paymentMethod;
}