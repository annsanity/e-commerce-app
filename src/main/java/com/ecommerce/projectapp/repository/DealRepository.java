package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long > {
}
