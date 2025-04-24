package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Find category by its ID
    Category findByCategoryId(String categoryId);

    // This is the same as findByCategoryId, just aliased for compatibility
    default Category findByCategory(String categoryId) {
        return findByCategoryId(categoryId);
    }

    // Find categories by level
    List<Category> findByLevel(Integer level);

    // Find child categories
    List<Category> findByParentCategory(Category parentCategory);
}