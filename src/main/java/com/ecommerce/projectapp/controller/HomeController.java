package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping
    public ResponseEntity<ApiResponse> home(){

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Ecommerce Multi Vendor System");

        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }
}
