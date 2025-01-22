package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product> {

    List<Product> findBySellerId(Long shopId);

    @Query("SELECT p from Product p WHERE (:query IS NULL OR LOWER(p.title) " +
    
}
