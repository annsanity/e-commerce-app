package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySeller_Id(Long sellerId);
}
