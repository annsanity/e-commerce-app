package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.HomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {
}
