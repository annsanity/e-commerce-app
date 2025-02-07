package com.ecommerce.projectapp.service.impl;

import com.ecommerce.projectapp.exception.ReviewNotFoundException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.Review;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.repository.ReviewRepository;
import com.ecommerce.projectapp.request.CreateReviewRequest;
import com.ecommerce.projectapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import javax.naming.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {

        Review newReview = new Review();
        newReview.setReviewText(req.getReviewText());
        newReview.setRating(req.getReviewRating());
        newReview.setProductImages(req.getProductImages());
        newReview.setUser(user);
        newReview.setProduct(product);
        product.getReviews().add(newReview);

        return reviewRepository.save(newReview);
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findReviewsByProduct_Id(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws ReviewNotFoundException, AuthenticationException {

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review Not found"));

        if(review.getUser().getId() != userId){
            throw new AuthenticationException("You do not have permission to delete this review");
        }
        review.setReviewText(reviewText);
        review.setRating(rating);
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws ReviewNotFoundException, AuthenticationException {

        Review review = reviewRepository.findById(reviewId).orElseThrow(()-> new ReviewNotFoundException("Review Not found"));

        if(review.getUser().getId() != userId){
            throw new AuthenticationException("You do not have permission to delete this review");
        }
        reviewRepository.delete(review);
    }
}
