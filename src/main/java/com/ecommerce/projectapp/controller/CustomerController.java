package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.model.Home;
import com.ecommerce.projectapp.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class CustomerController {

    private final HomeService homeService;

    @GetMapping("/page")
    public ResponseEntity<Home> getHomePage() {
        Home home = homeService.createHomePageData();
        return new ResponseEntity<>(home, HttpStatus.OK);
    }
}

