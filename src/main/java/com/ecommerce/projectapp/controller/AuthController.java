package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.VerificationCode;
import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.service.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sent/login-signup-otp")
    public ResponseEntity<ApiResponse> sentLoginOtp(
            @RequestBody VerificationCode req) throws MessagingException, UserException {

        authService.sentLoginOtp(req.getEmail());
        ApiResponse res = new ApiResponse();
        res.setMessage("Otp Sent");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }



}
