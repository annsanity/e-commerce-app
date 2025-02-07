package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.AccountStatus;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.model.HomeCategory;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.service.HomeCategoryService;
import com.ecommerce.projectapp.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final SellerService sellerService;
    private final HomeCategoryService homeCategoryService;

    @Autowired
    public AdminController(SellerService sellerService, HomeCategoryService homeCategoryService) {
        this.sellerService = sellerService;
        this.homeCategoryService = homeCategoryService;
    }

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(
            @PathVariable Long id, @PathVariable AccountStatus status) throws SellerException{
            Seller updatedSeller = sellerService.updateSellerAccountStatus(id, status);
            return ResponseEntity.ok(updatedSeller);
        }

    @GetMapping("/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory() throws Exception {
        List<HomeCategory> categories = homeCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    public ResponseEntity<HomeCategory> updateHomeCategory(
            @PathVariable Long id,
            @RequestBody HomeCategory homeCategory) throws Exception {
        HomeCategory updatedCategory = homeCategoryService.updateCategory(homeCategory, id);
        return ResponseEntity.ok(updatedCategory);
    }


}