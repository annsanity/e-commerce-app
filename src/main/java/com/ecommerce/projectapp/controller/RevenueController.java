package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.dto.RevenueChart;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.model.Seller;
import com.ecommerce.projectapp.service.RevenueService;
import com.ecommerce.projectapp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/revenue/chart")
public class RevenueController {

    private final RevenueService revenueService;
    private final SellerService sellerService;

    @GetMapping()
    public ResponseEntity<List<RevenueChart>> getRevenueChart(@RequestParam(defaultValue = "today") String type,
            @RequestHeader("Authorization") String jwt) throws SellerException {

        Seller seller = sellerService.getSellerProfile(jwt);
        List<RevenueChart> revenue = revenueService.getRevenueChartByType(type, seller.getId());

        return ResponseEntity.ok(revenue);
    }


}
