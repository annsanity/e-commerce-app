package com.ecommerce.projectapp.repository;

import com.ecommerce.projectapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewsByUserId(Long userId);
    List<Review> findReviewsByProduct_Id(Long productId);
}
