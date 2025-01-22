package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByCategory(String categoryId);

    List<Category> findByLevel(Integer level);
}
