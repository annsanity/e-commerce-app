package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.exception.ReviewNotFoundException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.Review;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(CreateReviewRequest req, User user, Product product);

    List<Review> getReviewsByProductId(Long productId);

    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws ReviewNotFoundException;

    void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException;
}
