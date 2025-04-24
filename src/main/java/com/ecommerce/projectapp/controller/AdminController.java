package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.exception.ProductException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.Product;
import com.ecommerce.projectapp.model.User;
import com.ecommerce.projectapp.request.CreateProductRequest;
import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.service.ProductService;
import com.ecommerce.projectapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Validate admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (!user.getRole().name().contains("ADMIN")) {
            throw new UserException("Access denied. Admin role required.");
        }

        // Create product without seller reference
        Product product = productService.createProduct(req);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product product,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Validate admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (!user.getRole().name().contains("ADMIN")) {
            throw new UserException("Access denied. Admin role required.");
        }

        Product updatedProduct = productService.updateProduct(productId, product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Validate admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (!user.getRole().name().contains("ADMIN")) {
            throw new UserException("Access denied. Admin role required.");
        }

        productService.deleteProduct(productId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Product deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestHeader("Authorization") String jwt) throws UserException {

        // Validate admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (!user.getRole().name().contains("ADMIN")) {
            throw new UserException("Access denied. Admin role required.");
        }

        List<Product> products = productService.getAllProductsForAdmin();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}