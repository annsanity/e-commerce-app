package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.exception.ReviewNotFoundException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.Review;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.request.CreateReviewRequest;
import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.service.ProductService;
import com.ecommerce.projectapp.service.ReviewService;
import com.ecommerce.projectapp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Product Reviews", description = "Review Creation, Retrieval, Update, and Deletion Endpoints")
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/products/{productId}/reviews")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/products/{productId}/reviews")
    public ResponseEntity<Review> writeReview(@Valid @RequestBody CreateReviewRequest req,
                                              @PathVariable Long productId,
                                              @RequestHeader("Authorization") String jwt)
            throws UserException, ProductException {

        User user = userService.findUserProfileByJwt(jwt);
        Product product = productService.findProductById(productId);
        Review review = reviewService.createReview(req, user, product);
        return ResponseEntity.ok(review);
    }

    @PatchMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@Valid @RequestBody CreateReviewRequest req,
                                               @PathVariable Long reviewId,
                                               @RequestHeader("Authorization") String jwt)
            throws UserException, ReviewNotFoundException {

        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.updateReview(
                reviewId,
                req.getReviewText(),
                req.getReviewRating(),
                user.getId()
        );
        return ResponseEntity.ok(review);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                    @RequestHeader("Authorization") String jwt)
            throws UserException, ReviewNotFoundException {

        User user = userService.findUserProfileByJwt(jwt);
        reviewService.deleteReview(reviewId, user.getId());
        ApiResponse res = new ApiResponse("Review deleted successfully", true);
        return ResponseEntity.ok(res);
    }
}