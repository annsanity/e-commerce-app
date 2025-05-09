package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.exception.WishlistNotFoundException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.model.Wishlist;
import com.ecommerce.projectapp.service.ProductService;
import com.ecommerce.projectapp.service.UserService;
import com.ecommerce.projectapp.service.WishlistService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist Management", description = "Wishlist Creation and Product Management Endpoints")
public class WishListController {

    private final WishlistService wishlistService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishlistByUserId(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Wishlist wishlist = wishlistService.getWishlistByUserId(user);
        return ResponseEntity.ok(wishlist);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishlist(@PathVariable Long productId,
                                                         @RequestHeader("Authorization") String jwt)
            throws WishlistNotFoundException, ProductException, UserException {

        Product product = productService.findProductById(productId);
        User user = userService.findUserProfileByJwt(jwt);
        Wishlist updatedWishlist = wishlistService.addProductToWishlist(user, product);
        return ResponseEntity.ok(updatedWishlist);
    }
}

