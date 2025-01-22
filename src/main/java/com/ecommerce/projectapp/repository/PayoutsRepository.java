package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.domain.PayoutsStatus;
import com.ecommerce.projectapp.model.Payouts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PayoutsRepository extends JpaRepository<Payouts, Long> {

    List<Payouts> findPayoutBySellerId(Long sellerId);
    List<Payouts> findAllByStatus(PayoutsStatus status);
}
