package com.ecommerce.projectapp.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
@Tag(name = "Home", description = "Public Home Page and Featured Content Endpoints")
public class CustomerController {

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getHomePage() {
        Map<String, Object> response = new HashMap<>();
        response.put("featured_products", true);
        response.put("new_arrivals", true);
        response.put("best_sellers", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}