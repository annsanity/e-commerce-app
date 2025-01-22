package com.ecommerce.projectapp.model;

import com.ecommerce.projectapp.domain.PayoutsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Payouts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Transaction> transactions = new ArrayList<>();

    @ManyToOne
    private Seller seller;

    private Long amount;

    private PayoutsStatus status = PayoutsStatus.PENDING;

    private LocalDateTime data = LocalDateTime.now();

}
