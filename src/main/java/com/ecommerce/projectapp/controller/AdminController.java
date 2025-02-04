package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.AccountStatus;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.service.HomeCategoryService;
import com.ecommerce.projectapp.service.SellerService;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstrucor
public class AdminController {

    private final SellerService SellerService;
    private final HomeCategoryService homeCategoryService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(
        @PathVariable Long id, @PathVariable AccountStatus status) throws SellerException{
            Seller updatedSeller = sellerService.updateSellerAccountStatus(id, status);
            return ResponseEntity.ok(updatedSeller);
        }

    

    
    

    
}