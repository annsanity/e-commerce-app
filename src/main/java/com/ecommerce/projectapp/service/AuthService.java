package com.ecommerce.projectapp.service;

import com.ecommerce.projectapp.request.LoginRequest;
import com.ecommerce.projectapp.request.SignupRequest;
import com.ecommerce.projectapp.response.AuthResponse;

public interface AuthService {
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signIn(LoginRequest req) throws Exception;
}
