package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.USER_ROLE;
import com.ecommerce.projectapp.exception.SellerException;
import com.ecommerce.projectapp.exception.UserException;
import com.ecommerce.projectapp.model.VerificationCode;
import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import com.ecommerce.projectapp.response.ApiResponse;
import com.ecommerce.projectapp.response.AuthResponse;
import com.ecommerce.projectapp.service.AuthService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
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

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid
            @RequestBody SignupRequest req)
            throws SellerException {


        String token = authService.createUser(req);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) throws SellerException {

        AuthResponse authResponse = authService.signIn(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }




}
