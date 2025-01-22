package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.domain.AccountStatus;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
