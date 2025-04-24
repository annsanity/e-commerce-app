package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.USER_ROLE;
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
@RequestMapping("/api/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestBody CreateProductRequest req,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Verify admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != USER_ROLE.ROLE_ADMIN) {
            throw new UserException("Access denied. Only admins can create products.");
        }

        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestHeader("Authorization") String jwt) throws UserException {

        // Verify admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != USER_ROLE.ROLE_ADMIN) {
            throw new UserException("Access denied. Only admins can access all products.");
        }

        List<Product> products = productService.getAllProductsForAdmin();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Verify admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != USER_ROLE.ROLE_ADMIN) {
            throw new UserException("Access denied. Only admins can access product details.");
        }

        Product product = productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product product,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Verify admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != USER_ROLE.ROLE_ADMIN) {
            throw new UserException("Access denied. Only admins can update products.");
        }

        Product updatedProduct = productService.updateProduct(productId, product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse> deleteProduct(
            @PathVariable Long productId,
            @RequestHeader("Authorization") String jwt) throws UserException, ProductException {

        // Verify admin access
        User user = userService.findUserProfileByJwt(jwt);
        if (user.getRole() != USER_ROLE.ROLE_ADMIN) {
            throw new UserException("Access denied. Only admins can delete products.");
        }

        productService.deleteProduct(productId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Product deleted successfully");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}