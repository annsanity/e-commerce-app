package com.ecommerce.projectapp.controller;

import com.ecommerce.projectapp.domain.USER_ROLE;
import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import com.ecommerce.projectapp.response.AuthResponse;
import com.ecommerce.projectapp.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody SignupRequest req)
            throws Exception {

        String token = authService.createUser(req);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Register Success");
        authResponse.setRole(USER_ROLE.ROLE_CUSTOMER);

        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest)
            throws Exception {

        AuthResponse authResponse = authService.signIn(loginRequest);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
}
